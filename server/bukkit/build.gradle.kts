import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml

plugins {
    id("java-library")
    alias(libs.plugins.shadow)
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.2.0"
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.2.0"
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.skriptlang.org/releases/")
    maven("https://repo.spongepowered.org/maven/")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":api-bukkit"))
    compileOnly(libs.skript)
    compileOnly(libs.spigot) {
        exclude(group = "net.md-5", module = "bungeecord-chat")
        exclude(group = "com.google.guava", module = "guava")
        exclude(group = "com.google.code.gson", module = "gson")
        exclude(group = "org.yaml", module = "snakeyaml")
        exclude(group = "junit", module = "junit")
    }
    compileOnly(libs.lombok)
    implementation(libs.gson)
    annotationProcessor(libs.lombok)
}

paperPluginYaml {
    name = "MinePay"
    version = project.rootProject.version as String
    main = "dk.minepay.server.bukkit.MinePayPlugin"
    description = "MinePay er et betalingssystem til MineClub servere."
    author = "MineClub"
    apiVersion = "1.13"
    dependencies {
        server("Skript", PaperPluginYaml.Load.BEFORE, false, joinClasspath = true)
    }
}

bukkitPluginYaml {
    name = "MinePay"
    version = project.rootProject.version as String
    main = "dk.minepay.server.bukkit.MinePayPlugin"
    description = "MinePay er et betalingssystem til MineClub servere."
    author = "MineClub"
    apiVersion = "1.13"
    softDepend = listOf("Skript")
}

tasks {
    withType<ShadowJar> {
        exclude("META-INF/**")
        minimize()
        relocate("com.google.gson", "dk.minepay.gson")
        relocate("com.google.errorprone", "dk.minepay.errorprone")
        relocate("io.socket", "dk.minepay.socketio")
        relocate("okhttp3", "dk.minepay.okhttp3")
        relocate("okio", "dk.minepay.okio")
        relocate("org.json", "dk.minepay.json")

        archiveFileName.set("../../../../build/libs/MinePay-Bukkit-${project.rootProject.version}.jar")
    }
}

group = rootProject.group
version = rootProject.version

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
