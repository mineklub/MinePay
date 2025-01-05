import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.vanniktech.maven.publish.SonatypeHost
import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar

plugins {
    id("java")
    alias(libs.plugins.shadow)
    id("com.vanniktech.maven.publish") version "0.30.0"
    signing
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.skriptlang.org/releases")
    maven("https://repo.spongepowered.org/maven/")
}

dependencies {
    implementation(project(":common"))
    compileOnly(libs.spigot) {
        exclude(group = "net.md-5", module = "bungeecord-chat")
        exclude(group = "com.google.guava", module = "guava")
        exclude(group = "com.google.code.gson", module = "gson")
        exclude(group = "org.yaml", module = "snakeyaml")
        exclude(group = "junit", module = "junit")
    }
    implementation(libs.okhttp) {
        exclude(group = "com.squareup.okio", module = "okio")
    }
    implementation(libs.socketclient) {
        exclude(group = "org.json", module = "json")
        exclude(group = "com.squareup.okio", module = "okio")
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
    }
    implementation(libs.okio)
    implementation(libs.gson)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.json)
    implementation(libs.configurate)
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
        relocate("org.spongepowered.configurate", "dk.minepay.configurate")
    }
    java {
        withJavadocJar()
        withSourcesJar()
    }
    signing {
        useGpgCmd()
    }
}

group = rootProject.group
version = rootProject.version

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    configure(JavaLibrary(
        javadocJar = JavadocJar.None(),
        sourcesJar = true,
    ))
    pom {
        name = rootProject.name
        description = "Payment system for servers on mineclub.dk"
        inceptionYear = "2024"
        url = "https://mineclub.dk/"
        licenses {
            license {
                name = "GNU GENERAL PUBLIC LICENSE"
                url = "https://www.gnu.org/licenses/gpl-3.0.html"
            }
        }
        developers {
            developer {
                id = "mineclub"
                name = "MineClub"
                url = "https://github.com/mineklub/"
            }
        }
        scm {
            url = "https://github.com/mineklub/MinePay/"
            connection = "scm:git:git://github.com/mineklub/MinePay.git"
            developerConnection = "scm:git:ssh://git@github.com/mineklub/MinePay.git"
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
