import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter.ofPattern

plugins {
    id("org.springframework.boot") version "3.1.1"
}
dependencies {

    implementation(project(":source:common:config"))

    implementation(project(":source:queue:config"))

    implementation(project(":source:notification:domain:email:config"))
    implementation(project(":source:notification:task:config"))
    implementation(project(":source:notification:domain:email:persistence:flyway"))
    implementation(project(":source:notification:rest"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
}

tasks.withType<Jar>() {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

var dateTimeAppender = now().format(ofPattern("yyyy-MM-dd-HH-mm"));

tasks.bootJar {
    archiveFileName.set("notification-app-" + dateTimeAppender + ".jar")
}