plugins {
    id("com.github.johnrengelman.shadow") version "7.0.0"
    kotlin("jvm") version "1.5.20"
}

val baseVersion = "1.0"

val ref = providers.environmentVariable("GITHUB_REF").forUseAtConfigurationTime()

version = if (ref.isPresent) {
    ref.get().replace("refs/tags/v", "")
} else {
    baseVersion
}

group = "de.kolpa.mc"

repositories {
    mavenCentral()
    maven(url = "https://papermc.io/repo/repository/maven-public/")
}

tasks {
    processResources {
        expand("version" to version)
    }
    compileKotlin {
        kotlinOptions {
            jvmTarget = "16"
        }
    }
}

tasks.register<Copy>("copyLibToPlugins") {
    dependsOn("shadowJar")
    from("$buildDir/libs")
    include("*.jar")
    into("C:\\Users\\kolya\\Desktop\\test\\plugins")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17-R0.1-SNAPSHOT")
    implementation("io.insert-koin:koin-core-jvm:3.1.1")
}