import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter.ofPattern

plugins {
    id("org.springframework.boot") version "3.1.1"
}
dependencies {
    implementation(project(":source:queue:config"))
    implementation(project(":source:billing:ui-thymeleaf"))
    implementation(project(":source:common:config"))
    implementation(project(":source:billing:domain:driver:config"))
    implementation(project(":source:billing:domain:driver:persistence:flyway"))
    implementation(project(":source:billing:domain:car:config"))
    implementation(project(":source:billing:domain:car:persistence:flyway"))
    implementation(project(":source:billing:domain:invoice:config"))
    implementation(project(":source:billing:domain:invoice:persistence:flyway"))
    implementation(project(":source:billing:domain:report:config"))
    implementation(project(":source:billing:domain:report:persistence:flyway"))
    implementation(project(":source:billing:domain:contract:config"))
    implementation(project(":source:billing:domain:contract:persistence:flyway"))
    implementation(project(":source:billing:domain:firm:config"))
    implementation(project(":source:billing:domain:firm:persistence:flyway"))
    implementation(project(":source:billing:domain:constant:config"))
    implementation(project(":source:billing:domain:constant:persistence:flyway"))
    implementation(project(":source:billing:domain:transaction:config"))
    implementation(project(":source:billing:domain:transaction:persistence:flyway"))
    implementation(project(":source:billing:domain:bonus:config"))
    implementation(project(":source:billing:domain:bonus:persistence:flyway"))
    implementation(project(":source:billing:domain:insurance:config"))
    implementation(project(":source:billing:domain:insurance:persistence:flyway"))
    implementation(project(":source:billing:domain:user:config"))
    implementation(project(":source:billing:domain:user:persistence:flyway"))
    implementation(project(":source:billing:task"))
    implementation(project(":source:billing:security:config"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
}

var dateTimeAppender = now().format(ofPattern("yyyy-MM-dd-HH-mm"));

tasks.bootJar {
    archiveFileName.set("billing-app-" + dateTimeAppender + ".jar")
}

tasks.withType<Jar>() {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}