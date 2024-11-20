import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    alias(libs.plugins.shadow)
    id("maven-publish")
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.skriptlang.org/releases")
}

dependencies {
    compileOnly(libs.spigot) {
        exclude(group = "net.md-5", module = "bungeecord-chat")
        exclude(group = "com.google.guava", module = "guava")
        exclude(group = "com.google.code.gson", module = "gson")
        exclude(group = "org.yaml", module = "snakeyaml")
        exclude(group = "junit", module = "junit")
    }
    compileOnly(libs.skript)
    implementation(libs.socketclient) {
        exclude(group = "org.json", module = "json")
        exclude(group = "com.squareup.okio", module = "okio")
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
    }
    implementation(libs.okhttp) {
        exclude(group = "com.squareup.okio", module = "okio")
    }
    implementation(libs.okio)
    implementation(libs.gson)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.json)
    implementation(project(":server-bukkit-skript-old"))
    implementation(project(":server-bukkit-skript-new"))
}

tasks {
    processResources {
        filesMatching("plugin.yml") {
            expand(mapOf("version" to project.rootProject.version))
        }
    }
    withType<ShadowJar> {
        exclude("META-INF/*")
        minimize()
    }
    java {
        withJavadocJar()
        withSourcesJar()
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "dk.minepay"
            artifactId = "minepay-bukkit"
            version = project.rootProject.version.toString()

            from(components["java"])

            pom {
                name = "MinePay"
                description = "A Bukkit plugin for MinePay"
                url = "https://mineclub.dk"
                inceptionYear = "2024"
                developers {
                    developer {
                        id = "mineclub"
                        name = "MineClub"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/mineklub/MinePay.git"
                    developerConnection = "scm:git:ssh://github.com/mineklub/MinePay.git"
                    url = "https://github.com/mineklub/MinePay"
                }
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
