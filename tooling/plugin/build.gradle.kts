// tooling-plugin: the Gradle init-script plugin injected into user projects.
plugins {
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

gradlePlugin {
    plugins {
        create("androidIDEPlugin") {
            id = "com.tom.rv2ide.gradle"
            implementationClass = "com.tom.rv2ide.gradle.AndroidIDEGradlePlugin"
        }
        create("androidIDEInitScriptPlugin") {
            id = "com.tom.rv2ide.gradle.init"
            implementationClass = "com.tom.rv2ide.gradle.AndroidIDEInitScriptPlugin"
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.4.0")
    implementation(project(":tooling-plugin-config"))
    compileOnly(gradleApi())
}
