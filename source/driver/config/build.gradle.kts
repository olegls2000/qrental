dependencies {
    implementation(project(":source:driver:core"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:driver:api:out"))
    implementation(project(":source:driver:persistence:flyway"))
    implementation(project(":source:driver:persistence:adapter"))
    implementation(project(":source:driver:persistence:repository"))
    implementation("org.springframework:spring-context")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}