dependencies {
    implementation(project(":source:transaction:core"))
    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:transaction:api:out"))
    implementation(project(":source:transaction:persistence:flyway"))
    implementation(project(":source:transaction:persistence:adapter"))
    implementation(project(":source:transaction:persistence:repository"))

    implementation(project(":source:callsign:api:in"))
    implementation(project(":source:driver:api:in"))

    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}