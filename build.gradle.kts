import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.serialization") version "1.3.61"
    application
}

group = "flavor.pie"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.socket:socket.io-client:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.14.0")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:3.8.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-Xinline-classes")
}

application {
    mainClassName = "wtfmachine.WtfApp"
}
