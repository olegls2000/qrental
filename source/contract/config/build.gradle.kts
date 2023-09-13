dependencies {
    implementation(project(":source:contract:core"))
    implementation(project(":source:contract:api:in"))
    implementation(project(":source:contract:api:out"))
    implementation(project(":source:contract:persistence:flyway"))
    implementation(project(":source:contract:persistence:adapter"))
    implementation(project(":source:contract:persistence:repository"))

    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:firm:api:in"))
    implementation(project(":source:email:api:in"))

    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("contract-config.jar")
}