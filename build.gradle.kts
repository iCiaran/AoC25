plugins {
    kotlin("jvm") version "2.2.20"
    application
}

group = "com.iciaran"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass = "com.iciaran.aoc25.MainKt"
}
