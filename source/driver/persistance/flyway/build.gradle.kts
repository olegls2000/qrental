plugins {
    id("q-java")
    id("org.springframework.boot") version "3.0.5"
}

dependencies{
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.0.5"))
    implementation("org.flywaydb:flyway-core")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    runtimeOnly("org.postgresql:postgresql")
}