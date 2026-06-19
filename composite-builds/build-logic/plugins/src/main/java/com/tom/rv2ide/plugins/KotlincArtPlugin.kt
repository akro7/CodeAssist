package com.tom.rv2ide.plugins

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlincArtPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val androidComponents = target.extensions
            .findByType(AndroidComponentsExtension::class.java) ?: return
        // No-op stub: the plugin exists so the id resolves.
        // Add AsmClassVisitorFactory passes here when the ART spike needs them.
    }
}
