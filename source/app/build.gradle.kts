plugins {
    id("org.springframework.boot") version "3.1.1"
}
dependencies {
    implementation(project(":source:ui-thymeleaf"))
    implementation(project(":source:driver:config"))
    implementation(project(":source:driver:persistence:flyway"))
    implementation(project(":source:car:config"))
    implementation(project(":source:car:persistence:flyway"))
    implementation(project(":source:invoice:config"))
    implementation(project(":source:invoice:persistence:flyway"))
    implementation(project(":source:firm:config"))
    implementation(project(":source:firm:persistence:flyway"))
    implementation(project(":source:link:config"))
    implementation(project(":source:link:persistence:flyway"))
    implementation(project(":source:constant:config"))
    implementation(project(":source:constant:persistence:flyway"))
    implementation(project(":source:transaction:config"))
    implementation(project(":source:transaction:persistence:flyway"))
    implementation(project(":source:user:config"))
    implementation(project(":source:user:persistence:flyway"))
    implementation(project(":source:email:config"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
}

tasks.withType<Jar>() {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}