repositories {
    mavenCentral()
}

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("com.diffplug.spotless")
}

group = "com.sirnuke.elusivebot"

if (project.hasProperty("internalMavenURL"))
{
    val internalMavenUsername: String by project
    val internalMavenPassword: String by project
    val internalMavenURL: String by project

    repositories {
        maven {
            credentials {
                username = internalMavenUsername
                password = internalMavenPassword
            }
            url = uri("$internalMavenURL/releases")
            name = "Internal-Maven-Releases"
        }
    }

    repositories {
        maven {
            credentials {
                username = internalMavenUsername
                password = internalMavenPassword
            }
            url = uri("$internalMavenURL/snapshots")
            name = "Internal-Maven-Snapshots"
        }
    }
} else {
    repositories {
        mavenLocal()
    }
}


spotless {
    kotlin {
        diktat()
    }
    kotlinGradle {
        diktat()
    }
}


dependencies {
    implementation("org.slf4j:slf4j-simple:2.0.12")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
