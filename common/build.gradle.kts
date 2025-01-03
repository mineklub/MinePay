plugins {
    id("java")
    alias(libs.plugins.shadow)
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.gson)
    implementation(libs.okhttp) {
        exclude(group = "com.squareup.okio", module = "okio")
    }
    implementation(libs.okio)
    implementation(libs.socketclient) {
        exclude(group = "org.json", module = "json")
        exclude(group = "com.squareup.okio", module = "okio")
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
    }
    implementation(libs.json)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
