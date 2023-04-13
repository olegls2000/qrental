plugins {
    id("q-java")
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation(project(":source:ui-thymeleaf"))
    
    implementation(project(":source:driver:config:spring"))
    implementation(project(":source:driver:core"))
    implementation(project(":source:driver:persistence:adapter"))
    implementation(project(":source:driver:persistence:repository:spring"))

    implementation(project(":source:car:config:spring"))
    implementation(project(":source:car:core"))
    implementation(project(":source:car:persistence:adapter"))
    implementation(project(":source:car:persistence:repository:spring"))

    implementation(project(":source:callsign:config:spring"))
    implementation(project(":source:callsign:core"))
    implementation(project(":source:callsign:persistence:adapter"))
    implementation(project(":source:callsign:persistence:repository:spring"))

    implementation(project(":source:invoice:config:spring"))
    implementation(project(":source:invoice:core"))
    implementation(project(":source:invoice:persistence:adapter"))
    implementation(project(":source:invoice:persistence:repository:spring"))

    implementation(project(":source:firm:config:spring"))
    implementation(project(":source:firm:core"))
    implementation(project(":source:firm:persistence:adapter"))
    implementation(project(":source:firm:persistence:repository:spring"))

    implementation(project(":source:link:config:spring"))
    implementation(project(":source:link:core"))
    implementation(project(":source:link:persistence:adapter"))
    implementation(project(":source:link:persistence:repository:spring"))

    implementation(project(":source:constant:config:spring"))
    implementation(project(":source:constant:core"))
    implementation(project(":source:constant:persistence:adapter"))
    implementation(project(":source:constant:persistence:repository:spring"))

    implementation(project(":source:transaction:config:spring"))
    implementation(project(":source:transaction:core"))
    implementation(project(":source:transaction:persistence:adapter"))
    implementation(project(":source:transaction:persistence:repository:spring"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(mapOf("group" to "org.junit.vintage", "module" to "junit-vintage-engine"))
    }
}

springBoot {
    mainClass.set("ee.qrental.Application")
}

tasks.withType<Jar>() {

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "Application"
    }

    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}

tasks.test {
    useJUnitPlatform()
}