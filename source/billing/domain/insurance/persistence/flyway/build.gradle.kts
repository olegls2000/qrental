dependencies {
    implementation(project(":source:common:utils"))
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    runtimeOnly("org.postgresql:postgresql")
}

tasks.jar {
    archiveFileName.set("billing-insurance-persistence-flyway.jar")
}