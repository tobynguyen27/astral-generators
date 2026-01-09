plugins {
    alias(libs.plugins.loom)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.spotless)
    idea
    `maven-publish`
}

val ENV = System.getenv()
val runNumber = ENV["GITHUB_RUN_NUMBER"] ?: "9999"
val isRelease = project.hasProperty("release")

val mod_version: String by project
val mod_id: String by project
val maven_group: String by project

version = if (isRelease) "${mod_version}" else "${mod_version}-build.${runNumber}"

group = maven_group

base.archivesName = mod_id

repositories {
    val repositories =
        setOf(
            "https://maven.parchmentmc.org",
            "https://maven.tobynguyen.dev/releases",
            "https://maven.tobynguyen.dev/snapshots",
            "https://maven.terraformersmc.com/",
            "https://mvn.devos.one/snapshots/",
            "https://maven.jamieswhiteshirt.com/libs-release",
            "https://server.bbkr.space/artifactory/libs-release",
            "https://maven.shedaniel.me/",
            "https://jitpack.io",
            "https://cursemaven.com",
            "https://api.modrinth.com/maven/",
        )

    repositories.forEach { maven { url = uri(it) } }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(
        loom.layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-1.18.2:2022.11.06@zip")
        }
    )

    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modImplementation(libs.fabric.kotlin)

    modImplementation(libs.codebebelib) { exclude(group = "io.github.fabricators_of_create") }
    modImplementation(libs.sense)
    modImplementation(libs.modmenu) { exclude(group = "net.fabricmc") }

    // API
    modCompileOnly(libs.rei.api)
    modCompileOnly(libs.jade.api)

    modImplementation(libs.portinglib)
    include(libs.portinglib)

    modImplementation(libs.forgetags)
    include(libs.forgetags)

    modImplementation(libs.registrate) { exclude(group = "io.github.fabricators_of_create") }
    include(libs.registrate) { exclude(group = "io.github.fabricators_of_create") }

    modImplementation(libs.libgui)
    include(libs.libgui)

    modImplementation(libs.energyapi)
    include(libs.energyapi)

    modLocalRuntime(libs.joml)
    modLocalRuntime(libs.sodium)
    modLocalRuntime(libs.indium)
    modLocalRuntime(libs.jade.fabric)
    modLocalRuntime(libs.jade.addons)
    modLocalRuntime(libs.lazydfu)
    modLocalRuntime(libs.rei.fabric) { exclude(group = "net.fabricmc") }
}

sourceSets {
    main {
        resources {
            srcDir("src/generated/resources")
            exclude(".cache")
        }
    }
}

loom {
    runs {
        register("datagen") {
            client()

            name("Data Generation")
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${file("src/generated/resources")}")
            vmArg("-Dfabric-api.datagen.modid=${mod_id}")
            vmArg("-Dporting_lib.datagen.existing_resources=${file("src/main/resources")}")
        }

        named("server") { runDir("run/server") }
        named("client") { programArgs(setOf("--username", "Toby")) }
    }
}

tasks.withType<ProcessResources>().configureEach {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    val mod_name: String by project
    val mod_id: String by project
    val mod_license: String by project
    val mod_version: String by project
    val mod_description: String by project

    val replaceProperties =
        hashMapOf(
            "loader_version" to libs.versions.fabric.loader.get(),
            "minecraft_version" to libs.versions.minecraft.get(),
            "mod_id" to mod_id,
            "mod_name" to mod_name,
            "mod_license" to mod_license,
            "mod_version" to mod_version,
            "mod_description" to mod_description,
        )

    inputs.properties(replaceProperties)

    filesMatching(setOf("fabric.mod.json")) { expand(replaceProperties) }
}

tasks.withType<JavaCompile>().configureEach { options.encoding = "UTF-8" }

java {
    toolchain { languageVersion = JavaLanguageVersion.of(17) }

    withSourcesJar()
}

tasks.named<Jar>("jar") {
    inputs.property("archivesName", project.base.archivesName)

    from("LICENSE") { rename { "${it}_${inputs.properties["archivesName"]}" } }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = mod_id
            version = project.version.toString()

            from(components["java"])
        }
    }

    repositories {
        val mavenUsername = ENV["MAVEN_USERNAME"]
        val mavenPassword = ENV["MAVEN_PASSWORD"]

        if (isRelease) {
            maven {
                url = uri("https://maven.tobynguyen.dev/releases")
                credentials {
                    username = mavenUsername
                    password = mavenPassword
                }
            }
        } else {
            maven {
                url = uri("https://maven.tobynguyen.dev/snapshots")
                credentials {
                    username = mavenUsername
                    password = mavenPassword
                }
            }
        }
    }
}

spotless {
    encoding("UTF-8")

    kotlin {
        ktfmt().kotlinlangStyle()
        endWithNewline()
        toggleOffOn()
    }

    kotlinGradle {
        target("*.gradle.kts")
        ktfmt().kotlinlangStyle()
    }

    java {
        importOrder()
        removeUnusedImports()
        palantirJavaFormat()
    }

    json {
        target("src/*/resources/**/*.json")
        targetExclude("src/generated/resources/**")

        biome("2.3.7")
            .downloadDir(File(rootDir, ".gradle/biome").absolutePath)
            .configPath(File(rootDir, "spotless/biome.json").absolutePath)

        endWithNewline()
    }
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}
