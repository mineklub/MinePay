import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.5"
}

group = "dk.minepay"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    implementation("dk.minepay:api-bukkit:0.0.4")
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT") {
        exclude(group = "net.md-5", module = "bungeecord-chat")
    }
}

tasks {
    withType<ShadowJar> {
        minimize()
        relocate("dk.minepay", "dk.mineclub.minepay")
        // Relocate dependencies fra MinePay til MineClub
        relocate("com.google.gson", "dk.mineclub.gson")
        relocate("com.google.errorprone", "dk.mineclub.errorprone")
        relocate("io.socket", "dk.mineclub.socketio")
        relocate("okhttp3", "dk.mineclub.okhttp3")
        relocate("okio", "dk.mineclub.okio")
        relocate("org.json", "dk.mineclub.json")
        relocate("org.spongepowered.configurate", "dk.mineclub.configurate")
        relocate("io.leangen.geantyref", "dk.mineclub.geantyref")
        relocate("org.yaml.snakeyaml", "dk.mineclub.snakeyaml")
        relocate("org.jspecify", "dk.mineclub.jspecify")
    }
    build {
        dependsOn("shadowJar")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}