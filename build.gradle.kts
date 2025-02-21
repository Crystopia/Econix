plugins {
    kotlin("jvm") version "2.0.20-Beta1"
    id("com.gradleup.shadow") version "9.0.0-beta8"
    id("io.papermc.paperweight.userdev") version "1.7.5"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("de.eldoria.plugin-yml.bukkit") version "0.7.0"
    kotlin("plugin.serialization") version "2.1.0"
}

val mcVersion = properties["minecraftVerions"] as String
val projectVersion = properties["version"] as String
val projectName = properties["name"] as String
val groupID = properties["group"] as String
val mainClass = properties["main"] as String
val projectDescription = properties["description"] as String
val twilightVersion = properties["twilightVersion"] as String
val commandAPIVersion = properties["commandAPIVersion"] as String
val adventureAPIVersion = properties["adventureAPIVersion"] as String

group = groupID
version = projectVersion

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.flyte.gg/releases")
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Paper
    paperweight.paperDevBundle("${mcVersion}-R0.1-SNAPSHOT")

    // Adventure
    implementation("net.kyori:adventure-api:${adventureAPIVersion}")

    // Twilight
    implementation("gg.flyte:twilight:${twilightVersion}")

    // Command API
    compileOnly("dev.jorel:commandapi-bukkit-core:${commandAPIVersion}")
    implementation("dev.jorel:commandapi-bukkit-shade-mojang-mapped:${commandAPIVersion}")
    implementation("dev.jorel:commandapi-bukkit-kotlin:${commandAPIVersion}")
}

kotlin {
    jvmToolchain(21)
}

tasks.build {
    dependsOn(tasks.shadowJar)
    dependsOn(tasks.reobfJar)
}

tasks {
    runServer {
        minecraftVersion(mcVersion)
    }
}

paper {
    name = projectName
    version = version
    description = projectDescription
    main = mainClass
    authors = listOf("xyzjesper")
    apiVersion = "1.19"
}
