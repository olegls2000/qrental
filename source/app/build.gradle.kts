plugins {
    id("q-java")
    id("org.springframework.boot") version "3.0.5"
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.0.5"))
    implementation(project(":source:ui-thymeleaf"))
    implementation(project(":source:driver:config:spring"))
    implementation(project(":source:driver:core"))
    implementation(project(":source:driver:persistance:adapter"))
    implementation(project(":source:driver:persistance:repository:spring"))


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