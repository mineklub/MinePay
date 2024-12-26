rootProject.name = "MinePay"

sequenceOf("bukkit").forEach { it ->
    val name = "server-$it"

    include(name)
    project(":${name}").projectDir = file(it)
}

sequenceOf("TestPlugin").forEach {
    val name = "examples-$it"

    include(name)
    project(":${name}").projectDir = file("examples/$it")
}