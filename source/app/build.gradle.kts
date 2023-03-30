plugins {
    id("q-java")
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation(project(":source:ui-thymeleaf"))
    implementation(project(":source:driver:config:spring"))
    implementation(project(":source:driver:core"))
    implementation(project(":source:driver:persistance:adapter"))
    implementation(project(":source:driver:persistance:repository:spring"))

    implementation(project(":source:callsign:config:spring"))
    implementation(project(":source:callsign:core"))
    implementation(project(":source:callsign:persistance:adapter"))
    implementation(project(":source:callsign:persistance:repository:spring"))


    implementation(project(":source:invoice:config:spring"))
    implementation(project(":source:invoice:core"))
    implementation(project(":source:invoice:persistance:adapter"))
    implementation(project(":source:invoice:persistance:repository:spring"))

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