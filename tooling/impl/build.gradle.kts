// tooling-impl: the Gradle daemon-side tooling server (runs inside the user's Gradle build as a fat JAR).
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<Jar> {
    manifest {
        attributes("Main-Class" to "com.tom.rv2ide.tooling.impl.Main")
    }
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("all")
    mergeServiceFiles()
    relocate("com.google.gson", "shadow.gson")
}

tasks.register<Copy>("copyJar") {
    dependsOn("shadowJar")
    from(layout.buildDirectory.dir("libs")) {
        include("*-all.jar")
        rename { "tooling-api-all.jar" }
    }
    into(layout.buildDirectory.dir("libs"))
}

tasks.named("build") {
    finalizedBy("copyJar")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.4.0")

    // Tooling API contract
    api(project(":tooling-api"))

    // TOML config parser
    implementation("io.hotmoka:toml4j:0.7.3")

    // Google Auto-Service (generates META-INF/services automatically)
    kapt("com.google.auto.service:auto-service:1.1.1")
    implementation("com.google.auto.service:auto-service-annotations:1.1.1")

    // Logging (logback-classic for structured log output)
    implementation("ch.qos.logback:logback-classic:1.4.14")
    runtimeOnly("org.slf4j:slf4j-simple:2.0.9")

    // XML parsing (xerces used by some model builders)
    implementation("xerces:xercesImpl:2.12.2")
    implementation("xml-apis:xml-apis:1.4.01")

    // Gradle tooling API — provided at runtime by the Gradle daemon
    compileOnly(gradleApi())
}
