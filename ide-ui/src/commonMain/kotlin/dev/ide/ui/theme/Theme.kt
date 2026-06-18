package dev.ide.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * CodeAssist design system — AKRO Edition
 * Accent: Orange (#F5820A) + Blue (#3B9EE8) on deep dark background
 * Developer: @A_KOJO | Telegram: 01508294415
 */

enum class CaAccent { Orange, Blue }

@Immutable
data class SyntaxColors(
    val default: Color,
    val keyword: Color,
    val storage: Color,
    val string: Color,
    val number: Color,
    val func: Color,
    val type: Color,
    val comment: Color,
    val property: Color,
    val variable: Color,
    val punctuation: Color,
    val constant: Color,
    val annotation: Color,
)

/** Solid puzzle-block palette for the projectional block editor. */
@Immutable
data class BlockColors(
    val control: Color,
    val data: Color,
    val call: Color,
    val ret: Color,
    val comment: Color,
    val method: Color,
    val op: Color,
    val text: Color,
    val socket: Color,
    val socketText: Color,
    val hole: Color,
)

private val DarkBlocks = BlockColors(
    data = Color(0xFF3B9EE8),
    control = Color(0xFFF5820A),
    call = Color(0xFF4A7FC1),
    ret = Color(0xFFC25B5B),
    comment = Color(0xFF5E626B),
    method = Color(0xFF2C8F80),
    op = Color(0xFF3E9E63),
    text = Color(0xFFFFFFFF),
    socket = Color.White.copy(alpha = 0.92f),
    socketText = Color(0xFF0D0F12),
    hole = Color.Black.copy(alpha = 0.30f),
)

private val LightBlocks = BlockColors(
    control = Color(0xFFF5820A),
    data = Color(0xFF3B9EE8),
    call = Color(0xFF3A6FB5),
    ret = Color(0xFFB44C4C),
    comment = Color(0xFF83868D),
    method = Color(0xFF1F8A77),
    op = Color(0xFF2F8F56),
    text = Color(0xFFFFFFFF),
    socket = Color.White.copy(alpha = 0.94f),
    socketText = Color(0xFF0D0F12),
    hole = Color.Black.copy(alpha = 0.26f),
)

@Immutable
data class CodeAssistColors(
    val isDark: Boolean,
    val bg: Color,
    val editorBg: Color,
    val consoleBg: Color,
    val surface: Color,
    val surface2: Color,
    val surface3: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textOnAccent: Color,
    val separator: Color,
    val separatorStrong: Color,
    val hairline: Color,
    val gutterText: Color,
    val currentLine: Color,
    val selection: Color,
    val accent: Color,
    val accentStrong: Color,
    val accentSoft: Color,
    val accentSecondary: Color,
    val success: Color,
    val run: Color,
    val warning: Color,
    val error: Color,
    val info: Color,
    val gitAdded: Color,
    val gitModified: Color,
    val gitDeleted: Color,
    val gitUntracked: Color,
    val glassThin: Color,
    val glassReg: Color,
    val glassThick: Color,
    val glassEdge: Color,
    val glassEdgeTop: Color,
    val scrim: Color,
    val syntax: SyntaxColors,
    val block: BlockColors,
)

private val DarkSyntax = SyntaxColors(
    default = Color(0xFFC7C8CF),
    keyword = Color(0xFF3B9EE8),
    storage = Color(0xFF3B9EE8),
    string = Color(0xFF98C97A),
    number = Color(0xFFF5820A),
    func = Color(0xFF61AFEF),
    type = Color(0xFFF5A623),
    comment = Color(0xFF6C7078),
    property = Color(0xFF57B6C2),
    variable = Color(0xFFE08C84),
    punctuation = Color(0xFF8B8D96),
    constant = Color(0xFFF5820A),
    annotation = Color(0xFFF5A623),
)

private val LightSyntax = SyntaxColors(
    default = Color(0xFF34363D),
    keyword = Color(0xFF1A7AC7),
    storage = Color(0xFF1A7AC7),
    string = Color(0xFF3F9C45),
    number = Color(0xFFD4700A),
    func = Color(0xFF3A6FE0),
    type = Color(0xFFD4700A),
    comment = Color(0xFFA3A4AA),
    property = Color(0xFF0A86A8),
    variable = Color(0xFFC0473F),
    punctuation = Color(0xFF6B6C73),
    constant = Color(0xFFD4700A),
    annotation = Color(0xFFD4700A),
)

// AKRO Dark — deep AMOLED black with orange + blue accents
private fun darkColors(accent: Color, accentStrong: Color, accentSecondary: Color) = CodeAssistColors(
    isDark = true,
    bg = Color(0xFF0D0F12),
    editorBg = Color(0xFF111316),
    consoleBg = Color(0xFF0A0C0E),
    surface = Color(0xFF181A1F),
    surface2 = Color(0xFF1E2025),
    surface3 = Color(0xFF26282E),
    textPrimary = Color(0xFFEAEBEE),
    textSecondary = Color(0xFFA0A2AE),
    textTertiary = Color(0xFF6A6C78),
    textOnAccent = Color(0xFFFFFFFF),
    separator = Color.White.copy(alpha = 0.08f),
    separatorStrong = Color.White.copy(alpha = 0.13f),
    hairline = Color.White.copy(alpha = 0.06f),
    gutterText = Color(0xFF4A4C56),
    currentLine = Color.White.copy(alpha = 0.04f),
    selection = accent.copy(alpha = 0.30f),
    accent = accent,
    accentStrong = accentStrong,
    accentSoft = accent.copy(alpha = 0.15f),
    accentSecondary = accentSecondary,
    success = Color(0xFF34D058),
    run = Color(0xFF34D058),
    warning = Color(0xFFFFB340),
    error = Color(0xFFFF6B63),
    info = Color(0xFF3B9EE8),
    gitAdded = Color(0xFF34D058),
    gitModified = Color(0xFFF5820A),
    gitDeleted = Color(0xFFFF6B63),
    gitUntracked = Color(0xFF3B9EE8),
    glassThin = Color(0xFF141618).copy(alpha = 0.55f),
    glassReg = Color(0xFF161820).copy(alpha = 0.72f),
    glassThick = Color(0xFF0E1014).copy(alpha = 0.88f),
    glassEdge = Color.White.copy(alpha = 0.09f),
    glassEdgeTop = Color.White.copy(alpha = 0.14f),
    scrim = Color.Black.copy(alpha = 0.55f),
    syntax = DarkSyntax,
    block = DarkBlocks,
)

private fun lightColors(accent: Color, accentStrong: Color, accentSecondary: Color) = CodeAssistColors(
    isDark = false,
    bg = Color(0xFFEEEDF0),
    editorBg = Color(0xFFFAF9FC),
    consoleBg = Color(0xFFFAF9FC),
    surface = Color(0xFFFFFFFF),
    surface2 = Color(0xFFF3F2F5),
    surface3 = Color(0xFFE8E7EC),
    textPrimary = Color(0xFF1A1B20),
    textSecondary = Color(0xFF5E606C),
    textTertiary = Color(0xFF97989F),
    textOnAccent = Color(0xFFFFFFFF),
    separator = Color.Black.copy(alpha = 0.09f),
    separatorStrong = Color.Black.copy(alpha = 0.14f),
    hairline = Color.Black.copy(alpha = 0.06f),
    gutterText = Color(0xFFB3B2B8),
    currentLine = Color.Black.copy(alpha = 0.03f),
    selection = accent.copy(alpha = 0.22f),
    accent = accent,
    accentStrong = accentStrong,
    accentSoft = accent.copy(alpha = 0.12f),
    accentSecondary = accentSecondary,
    success = Color(0xFF29A847),
    run = Color(0xFF29A847),
    warning = Color(0xFFD98300),
    error = Color(0xFFDF4A45),
    info = Color(0xFF1A7AC7),
    gitAdded = Color(0xFF29A847),
    gitModified = Color(0xFFD4700A),
    gitDeleted = Color(0xFFDF4A45),
    gitUntracked = Color(0xFF1A7AC7),
    glassThin = Color.White.copy(alpha = 0.55f),
    glassReg = Color(0xFFFCFBFF).copy(alpha = 0.72f),
    glassThick = Color(0xFFF8F7FC).copy(alpha = 0.88f),
    glassEdge = Color.Black.copy(alpha = 0.08f),
    glassEdgeTop = Color.White.copy(alpha = 0.9f),
    scrim = Color(0xFF0D0F12).copy(alpha = 0.35f),
    syntax = LightSyntax,
    block = LightBlocks,
)

// Orange accent = primary, Blue = secondary
fun caColors(dark: Boolean, accent: CaAccent): CodeAssistColors = when {
    dark && accent == CaAccent.Orange -> darkColors(Color(0xFFF5820A), Color(0xFFE8730A), Color(0xFF3B9EE8))
    dark && accent == CaAccent.Blue   -> darkColors(Color(0xFF3B9EE8), Color(0xFF2A8FDE), Color(0xFFF5820A))
    !dark && accent == CaAccent.Orange -> lightColors(Color(0xFFE07208), Color(0xFFD06500), Color(0xFF1A7AC7))
    else -> lightColors(Color(0xFF1A7AC7), Color(0xFF1468B0), Color(0xFFE07208))
}

// ---- Spacing (4-pt scale) & radius ----
@Immutable
object Spacing {
    val s1 = 4.dp; val s2 = 8.dp; val s3 = 12.dp; val s4 = 16.dp; val s5 = 20.dp
    val s6 = 24.dp; val s7 = 28.dp; val s8 = 32.dp; val s10 = 40.dp; val s12 = 48.dp
}

@Immutable
object Radius {
    val xs = 6.dp; val sm = 9.dp; val control = 12.dp; val md = 14.dp
    val lg = 18.dp; val xl = 24.dp; val sheet = 26.dp; val pill = 999.dp
}

// ---- Typography ----
@Immutable
class CaTypography(ui: FontFamily, code: FontFamily) {
    val caption2 = TextStyle(fontFamily = ui, fontSize = 11.sp, fontWeight = FontWeight.Normal)
    val caption = TextStyle(fontFamily = ui, fontSize = 12.sp, fontWeight = FontWeight.Normal)
    val footnote = TextStyle(fontFamily = ui, fontSize = 13.sp, fontWeight = FontWeight.Normal)
    val subhead = TextStyle(fontFamily = ui, fontSize = 15.sp, fontWeight = FontWeight.Normal)
    val body = TextStyle(fontFamily = ui, fontSize = 16.sp, fontWeight = FontWeight.Normal)
    val headline = TextStyle(fontFamily = ui, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
    val title3 = TextStyle(fontFamily = ui, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    val title2 = TextStyle(fontFamily = ui, fontSize = 23.sp, fontWeight = FontWeight.SemiBold)
    val title1 = TextStyle(fontFamily = ui, fontSize = 28.sp, fontWeight = FontWeight.Bold)
    val large = TextStyle(fontFamily = ui, fontSize = 34.sp, fontWeight = FontWeight.Bold, lineHeight = 40.sp)
    val code = TextStyle(fontFamily = code, fontSize = 13.5f.sp, lineHeight = 22.sp)
    val codeSmall = TextStyle(fontFamily = code, fontSize = 12.5f.sp, lineHeight = 20.sp)
    val uiFamily = ui
    val codeFamily = code
}

val LocalCaColors = staticCompositionLocalOf<CodeAssistColors> { error("CodeAssistTheme not applied") }
val LocalCaType = staticCompositionLocalOf<CaTypography> { error("CodeAssistTheme not applied") }

/** Token accessor: `Ca.colors`, `Ca.type`, `Ca.spacing`, `Ca.radius`. */
object Ca {
    val colors: CodeAssistColors
        @Composable @ReadOnlyComposable get() = LocalCaColors.current
    val type: CaTypography
        @Composable @ReadOnlyComposable get() = LocalCaType.current
    val spacing get() = Spacing
    val radius get() = Radius
}

@Composable
fun CodeAssistTheme(
    dark: Boolean = true,
    accent: CaAccent = CaAccent.Orange,
    uiFont: FontFamily = FontFamily.SansSerif,
    codeFont: FontFamily = FontFamily.Monospace,
    content: @Composable () -> Unit,
) {
    val colors = remember(dark, accent) { caColors(dark, accent) }
    val type = remember(uiFont, codeFont) { CaTypography(uiFont, codeFont) }
    CompositionLocalProvider(
        LocalCaColors provides colors,
        LocalCaType provides type,
        content = content,
    )
}
