rootProject.name = "MinePay"

include("common")
project(":common").projectDir = file("common")
sequenceOf("bukkit").forEach { it ->
    val name = "server-$it"
    if (file("server/$it").exists()) {
        include(name)
        project(":$name").projectDir = file("server/$it")
    }

    val apiName = "api-$it"
    if (file("api/$it").exists()) {
        include(apiName)
        project(":$apiName").projectDir = file("api/$it")
    }
}
