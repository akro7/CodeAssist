// tooling-api: the RPC contract the IDE uses to talk to the Gradle tooling daemon.
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
    api(project(":tooling-events"))
    api(project(":tooling-model"))
    api("com.google.code.gson:gson:2.10.1")
    // AGP builder model (for builder-model types referenced by ToolingApiLauncher)
    compileOnly("com.android.tools.build:gradle-api:8.7.3")
}
