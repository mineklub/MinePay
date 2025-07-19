import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    alias(libs.plugins.shadow)
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.skriptlang.org/releases/")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":api-bukkit"))
    compileOnly(libs.skript.old)
    compileOnly(libs.lombok)
    implementation(libs.gson)
    annotationProcessor(libs.lombok)
    compileOnly(libs.spigot) {
        exclude(group = "net.md-5", module = "bungeecord-chat")
        exclude(group = "com.google.guava", module = "guava")
        exclude(group = "com.google.code.gson", module = "gson")
        exclude(group = "org.yaml", module = "snakeyaml")
        exclude(group = "junit", module = "junit")
        exclude(group ="commons-lang", module = "commons-lang")
    }
}

tasks {
    withType<ShadowJar> {
        exclude("META-INF/**")
        minimize()
        relocate("com.google.gson", "dk.minepay.gson")
    }
}

group = rootProject.group
version = rootProject.version

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
