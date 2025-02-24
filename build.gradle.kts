import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.0.20-Beta1"
    id("com.gradleup.shadow") version "8.3.3"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("de.eldoria.plugin-yml.bukkit") version "0.7.1"
    kotlin("plugin.serialization") version "2.1.0"
    id("maven-publish")
}

val mcVersion = properties["minecraftVerions"] as String
val projectVersion = properties["version"] as String
val projectName = properties["name"] as String
val groupID = properties["group"] as String
val mainClass = properties["main"] as String
val projectDescription = properties["description"] as String
val twilightVersion = properties["twilightVersion"] as String
val commandAPIVersion = properties["commandAPIVersion"] as String

group = groupID
version = projectVersion

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.flyte.gg/releases")
    maven("https://jitpack.io")
    maven("https://repo.extendedclip.com/releases/")
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Paper
    compileOnly("io.papermc.paper:paper-api:${mcVersion}-R0.1-SNAPSHOT")

    // Twilight
    implementation("gg.flyte:twilight:${twilightVersion}")

    // Command API
    compileOnly("dev.jorel:commandapi-bukkit-core:${commandAPIVersion}")
    implementation("dev.jorel:commandapi-bukkit-shade-mojang-mapped:${commandAPIVersion}")
    implementation("dev.jorel:commandapi-bukkit-kotlin:${commandAPIVersion}")

    // Vault
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")

    // PAPI
    compileOnly("me.clip:placeholderapi:2.11.6")

    // SQL
    implementation("org.ktorm:ktorm-core:3.6.0")
    implementation("org.ktorm:ktorm-support-sqlite:3.6.0")
    implementation("mysql:mysql-connector-java:8.0.33")
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<ShadowJar> {
    manifest {
        attributes["paperweight-mappings-namespace"] = "mojang"
    }
    relocate("dev.jorel.commandapi", "me.jesforge.econix.commandapi")
}


tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks {
    runServer {
        minecraftVersion(mcVersion)
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Crystopia/Econix")
            credentials {
                username = System.getenv("GITHUB_USERNAME") ?: "USERNAME"
                password = System.getenv("GH_PACKAGES_TOKEN") ?: "TOKEN"
            }
        }
    }
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            artifactId = "econix"
        }
    }
}

bukkit {
    name = projectName
    version = version
    description = projectDescription
    main = mainClass
    authors = listOf("jesforge")
    apiVersion = "1.19"
    softDepend = listOf("PlaceholderAPI", "Vault")
}
