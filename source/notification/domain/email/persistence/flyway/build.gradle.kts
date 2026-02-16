dependencies {
    implementation("org.flywaydb:flyway-core:10.19.0")
    implementation("org.flywaydb:flyway-database-postgresql:10.19.0")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    runtimeOnly("org.postgresql:postgresql")
}
tasks.jar {
    archiveFileName.set("notification-email-persistence-flyway.jar")
}
