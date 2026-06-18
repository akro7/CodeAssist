// tooling-builder-model-impl: Gradle builder model implementation (AGP module info, variants, etc.)
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

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.4.0")
    // Gradle tooling API (provided at runtime by Gradle)
    compileOnly(gradleApi())
}
