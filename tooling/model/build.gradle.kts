// tooling-model: data-model exchanged over the tooling protocol (project info, classpath, AGP variants, etc.)
plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.4.0")
    api("com.google.code.gson:gson:2.10.1")
    // AGP builder model v2 (for com.android.builder.model.v2.*)
    compileOnly("com.android.tools.build:gradle-api:8.7.3")
    // LemMinX DOM parser (XML model builder)
    implementation("org.eclipse.lemminx:org.eclipse.lemminx:0.27.0")
    // Gradle tooling API (provided at runtime)
    compileOnly(gradleApi())
}
