dependencies {
    implementation(project(":source:callsign:core"))
    implementation(project(":source:callsign:api:in"))
    implementation(project(":source:callsign:api:out"))
    implementation(project(":source:callsign:persistence:flyway"))
    implementation(project(":source:callsign:persistence:adapter"))
    implementation(project(":source:callsign:persistence:repository"))

    implementation(project(":source:driver:api:in"))
    implementation("org.springframework:spring-context")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}