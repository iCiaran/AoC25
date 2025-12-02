plugins {
    kotlin("jvm") version "2.2.20"
    application
    id("org.jetbrains.kotlinx.benchmark") version "0.4.14"
    kotlin("plugin.allopen") version "2.0.20"
}

group = "com.iciaran"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    create("benchmark")
}

kotlin {
    target {
        compilations.getByName("benchmark")
            .associateWith(compilations.getByName("main"))
    }

    sourceSets {
        benchmark {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.14")
            }
        }
    }
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}

benchmark {

    targets {
        register("benchmark")
    }

    configurations {
        named("main") {
            val filter = project.findProperty("bench")?.toString()
            if (filter != null) {
                include(filter)
            }
        }
    }
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
