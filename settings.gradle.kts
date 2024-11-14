rootProject.name = "MinePay"

sequenceOf("bukkit").forEach {
    val name = "server-$it"

    include(name)
    project(":${name}").projectDir = file(it)
}