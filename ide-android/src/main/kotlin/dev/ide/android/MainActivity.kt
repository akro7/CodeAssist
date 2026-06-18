package dev.ide.android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import dev.ide.ui.CodeAssistApp
import dev.ide.ui.backend.FileActions
import dev.ide.ui.backend.IdeBackend
import dev.ide.ui.backend.TreeNode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * The Android host. Bootstraps the real on-device engine ([AndroidIde]) off the main thread,
 * renders the shared Compose IDE UI ([CodeAssistApp]) over the resulting [IdeBackend].
 * An AKRO-branded splash shows while the engine starts.
 */
class MainActivity : ComponentActivity() {

    private var session: AndroidIde.Session? = null

    /** A file handed in by another app ("Open with" / "Share to"), pending import once the engine is up. */
    private val inbound = mutableStateOf<Uri?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        inbound.value = extractStream(intent)
        setContent {
            var backend by remember { mutableStateOf<IdeBackend?>(null) }
            var error by remember { mutableStateOf<String?>(null) }
            LaunchedEffect(Unit) {
                runCatching { withContext(Dispatchers.IO) { AndroidIde.bootstrap(applicationContext) } }
                    .onSuccess { s -> session = s; backend = s.backend }
                    .onFailure { e -> error = e.message ?: e.toString() }
            }

            var pendingTarget by remember { mutableStateOf<String?>(null) }
            var pendingCallback by remember { mutableStateOf<((List<String>) -> Unit)?>(null) }
            val importLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.OpenMultipleDocuments()
            ) { uris ->
                val b = backend
                val target = pendingTarget
                val created = if (b != null && target != null) {
                    uris.mapNotNull { importUri(it, target, b) }
                } else emptyList()
                pendingCallback?.invoke(created)
                pendingTarget = null; pendingCallback = null
            }
            val fileActions = remember {
                object : FileActions {
                    override val canImport: Boolean = true
                    override fun importInto(targetDir: String, onImported: (List<String>) -> Unit) {
                        pendingTarget = targetDir
                        pendingCallback = onImported
                        importLauncher.launch(arrayOf("text/*", "application/json", "application/xml", "*/*"))
                    }
                    override val canShare: Boolean = true
                    override fun share(path: String) = shareFile(path)
                    override val canOpenUrl: Boolean = true
                    override fun openUrl(url: String) = openInBrowser(url)
                }
            }

            LaunchedEffect(backend, inbound.value) {
                val b = backend
                val uri = inbound.value
                if (b != null && uri != null) {
                    val target = firstSourceRoot(b.fileTree())
                    val path = if (target != null) importUri(uri, target, b) else null
                    Toast.makeText(
                        this@MainActivity,
                        if (path != null) "Imported ${File(path).name}" else "Couldn't import file",
                        Toast.LENGTH_SHORT,
                    ).show()
                    inbound.value = null
                }
            }

            val b = backend
            when {
                b != null -> CodeAssistApp(b, fileActions = fileActions)
                error != null -> Splash("Failed to start: $error")
                else -> Splash("Starting CodeAssist…")
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        extractStream(intent)?.let { inbound.value = it }
    }

    override fun onDestroy() {
        super.onDestroy()
        session?.backend?.close()
    }

    private fun importUri(uri: Uri, targetDir: String, backend: IdeBackend): String? = runCatching {
        val name = queryDisplayName(uri) ?: "imported-${System.currentTimeMillis()}"
        val bytes = contentResolver.openInputStream(uri)?.use { it.readBytes() } ?: return null
        backend.createFileBytes(targetDir, name, bytes)
    }.getOrNull()

    private fun shareFile(path: String) = runCatching {
        val uri = FileProvider.getUriForFile(this, "$packageName.fileprovider", File(path))
        val send = Intent(Intent.ACTION_SEND).apply {
            type = if (path.endsWith(".zip")) "application/zip" else "text/plain"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(send, "Share ${File(path).name}"))
    }.getOrElse { Toast.makeText(this, "Can't share this file", Toast.LENGTH_SHORT).show() }

    private fun openInBrowser(url: String) = runCatching {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }.getOrElse { Toast.makeText(this, "No app to open links", Toast.LENGTH_SHORT).show() }

    private fun queryDisplayName(uri: Uri): String? = runCatching {
        if (uri.scheme == "file") return uri.lastPathSegment
        contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { c ->
            if (c.moveToFirst()) c.getString(0) else null
        }
    }.getOrNull()

    private fun extractStream(intent: Intent?): Uri? = when (intent?.action) {
        Intent.ACTION_VIEW -> intent.data
        Intent.ACTION_SEND -> @Suppress("DEPRECATION") (intent.getParcelableExtra(Intent.EXTRA_STREAM) as? Uri)
        else -> null
    }
}

private fun firstSourceRoot(root: TreeNode): String? {
    root.sourceRootPath?.let { return it }
    for (child in root.children) firstSourceRoot(child)?.let { return it }
    return null
}

/**
 * AKRO-branded splash screen shown while the IDE engine boots.
 *
 * Design:
 * - App icon displayed inside a **pure circle clip** — no visible border/ring, just the icon shape.
 * - A subtle radial glow behind the icon lifts it off the dark background naturally.
 * - Slow pulse animation on the icon (scale 0.95 ↔ 1.05, 1.6 s cycle).
 * - "CodeAssist" title + "AKRO EDITION" badge in the AKRO orange accent.
 * - Slim circular progress indicator in AKRO orange with a blue track.
 */
@Composable
private fun Splash(message: String) {
    val bg     = Color(0xFF0A0A0F)
    val accent = Color(0xFFF5820A)   // AKRO orange
    val blue   = Color(0xFF3B9EE8)   // AKRO blue

    val pulse = rememberInfiniteTransition(label = "logo-pulse")
    val scale by pulse.animateFloat(
        initialValue = 0.95f,
        targetValue  = 1.05f,
        animationSpec = infiniteRepeatable(
            animation  = tween(durationMillis = 1600),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "logoScale",
    )

    Box(
        Modifier.fillMaxSize().background(bg),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            // ── Logo mark: circle-clipped icon + radial glow ──────────────────
            Box(
                Modifier
                    .size(108.dp)
                    .scale(scale)
                    .background(
                        brush = Brush.radialGradient(
                            0.0f to accent.copy(alpha = 0.20f),
                            0.55f to accent.copy(alpha = 0.07f),
                            1.0f to Color.Transparent,
                        ),
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                // painterResource = no external library, zero border, pure circle
                Image(
                    painter = painterResource(id = R.mipmap.ic_launcher),
                    contentDescription = "CodeAssist",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape),
                )
            }

            Spacer(Modifier.height(26.dp))

            // ── App name ──────────────────────────────────────────────────────
            Text(
                text = "CodeAssist",
                color = Color(0xFFF0F0F5),
                fontSize = 23.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.3.sp,
            )
            Text(
                text = "AKRO EDITION",
                color = accent,
                fontSize = 10.5f.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.8.sp,
                modifier = Modifier.padding(top = 3.dp),
            )

            Spacer(Modifier.height(40.dp))

            // ── Slim loading ring ─────────────────────────────────────────────
            CircularProgressIndicator(
                modifier   = Modifier.size(30.dp),
                color      = accent,
                trackColor = blue.copy(alpha = 0.18f),
                strokeWidth = 2.5.dp,
                strokeCap  = StrokeCap.Round,
            )

            Spacer(Modifier.height(14.dp))

            Text(
                text      = message,
                color     = Color(0xFF6A6A7A),
                fontSize  = 12.sp,
                textAlign = TextAlign.Center,
                modifier  = Modifier.padding(horizontal = 44.dp),
            )
        }
    }
}
