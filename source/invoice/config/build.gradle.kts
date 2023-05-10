dependencies {
    implementation(project(":source:invoice:core"))
    implementation(project(":source:invoice:api:in"))
    implementation(project(":source:invoice:api:out"))
    implementation(project(":source:invoice:persistence:flyway"))
    implementation(project(":source:invoice:persistence:adapter"))
    implementation(project(":source:invoice:persistence:repository"))

    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:firm:api:in"))
    implementation(project(":source:email:api:in"))
    implementation(project(":source:balance:api:in"))

    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("invoice-config.jar")
}