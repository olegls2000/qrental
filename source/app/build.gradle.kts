plugins {
    id("org.springframework.boot") version "3.0.5"
}
dependencies {
    implementation(project(":source:ui-thymeleaf"))

    implementation(project(":source:driver:api:in"))
    implementation(project(":source:driver:config"))
    implementation(project(":source:driver:core"))
    implementation(project(":source:driver:persistence:adapter"))
    implementation(project(":source:driver:persistence:repository"))
    implementation(project(":source:driver:persistence:flyway"))

    implementation(project(":source:car:config"))
    implementation(project(":source:car:core"))
    implementation(project(":source:car:persistence:adapter"))
    implementation(project(":source:car:persistence:repository"))
    implementation(project(":source:car:persistence:flyway"))

    implementation(project(":source:callsign:config"))
    implementation(project(":source:callsign:core"))
    implementation(project(":source:callsign:persistence:adapter"))
    implementation(project(":source:callsign:persistence:repository"))
    implementation(project(":source:callsign:persistence:flyway"))

    implementation(project(":source:invoice:config"))
    implementation(project(":source:invoice:core"))
    implementation(project(":source:invoice:persistence:adapter"))
    implementation(project(":source:invoice:persistence:repository"))
    implementation(project(":source:invoice:persistence:flyway"))

    implementation(project(":source:firm:config"))
    implementation(project(":source:firm:core"))
    implementation(project(":source:firm:persistence:adapter"))
    implementation(project(":source:firm:persistence:repository"))
    implementation(project(":source:firm:persistence:flyway"))

    implementation(project(":source:link:config"))
    implementation(project(":source:link:core"))
    implementation(project(":source:link:persistence:adapter"))
    implementation(project(":source:link:persistence:repository"))
    implementation(project(":source:link:persistence:flyway"))

    implementation(project(":source:constant:config"))
    implementation(project(":source:constant:core"))
    implementation(project(":source:constant:persistence:adapter"))
    implementation(project(":source:constant:persistence:repository"))
    implementation(project(":source:constant:persistence:flyway"))

    implementation(project(":source:transaction:config"))
    implementation(project(":source:transaction:core"))
    implementation(project(":source:transaction:persistence:adapter"))
    implementation(project(":source:transaction:persistence:repository"))
    implementation(project(":source:transaction:persistence:flyway"))

    implementation(project(":source:balance:config"))
    implementation(project(":source:balance:core"))
    implementation(project(":source:balance:persistence:adapter"))
    implementation(project(":source:balance:persistence:repository"))
    implementation(project(":source:balance:persistence:flyway"))

    implementation(project(":source:email:api:in"))
    implementation(project(":source:email:core"))
    implementation(project(":source:email:config"))

    implementation("org.springframework.boot:spring-boot-starter")
}

tasks.withType<Jar>() {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

}