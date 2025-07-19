rootProject.name = "MinePay"

include("common")
project(":common").projectDir = file("common")
sequenceOf("skript-2_9", "skript-2_10").forEach { name ->
    val hookName = "hook-$name"
    if (file("hooks/$name").exists()) {
        include(hookName)
        project(":$hookName").projectDir = file("hooks/$name")
    }
}

sequenceOf("bukkit").forEach {
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
