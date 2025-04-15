dependencies {
    implementation(project(":source:common:utils"))
    implementation(project(":source:billing:domain:transaction:domain"))
    implementation("org.flywaydb:flyway-core")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    runtimeOnly("org.postgresql:postgresql")
}

tasks.jar {
    archiveFileName.set("billing-transaction-persistence-flyway.jar")
}