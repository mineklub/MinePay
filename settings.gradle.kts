rootProject.name = "MinePay"

sequenceOf("bukkit").forEach { it ->
    val name = "server-$it"

    include(name)
    project(":${name}").projectDir = file(it)
    if (it == "bukkit") {
        sequenceOf("new", "old").forEach { it2 ->
            val subName = "$name-skript-$it2"
            include(subName)
            project(":$subName").projectDir = file("bukkit/skript/$it2")
        }
    }
}

sequenceOf("TestPlugin").forEach {
    val name = "examples-$it"

    include(name)
    project(":${name}").projectDir = file("examples/$it")
}
