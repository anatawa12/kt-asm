import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.61"
}

group = "com.anatawa12"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val asmVersion = "7.2"

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.ow2.asm:asm:$asmVersion")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xinline-classes", "-Xuse-experimental=kotlin.Experimental")
        }
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
