dependencies {
    implementation(project(":source:transaction:core"))
    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:transaction:api:out"))
    implementation(project(":source:email:api:in"))
    implementation(project(":source:user:api:in"))
    implementation(project(":source:transaction:persistence:flyway"))
    implementation(project(":source:transaction:persistence:adapter"))
    implementation(project(":source:transaction:persistence:repository"))

    implementation(project(":source:driver:api:in"))
    implementation(project(":source:car:api:in"))
    implementation(project(":source:constant:api:in"))

    implementation("org.springframework:spring-context-support")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("transaction-config.jar")
}