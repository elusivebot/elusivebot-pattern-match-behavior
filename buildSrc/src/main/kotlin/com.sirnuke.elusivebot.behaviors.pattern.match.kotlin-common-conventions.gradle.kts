repositories {
    mavenCentral()
    mavenLocal()
}

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("com.diffplug.spotless")
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
