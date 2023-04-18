dependencies {
    implementation(project(":source:constant:core"))
    implementation(project(":source:constant:api:in"))
    implementation(project(":source:constant:api:out"))
    implementation(project(":source:constant:persistence:flyway"))
    implementation(project(":source:constant:persistence:adapter"))
    implementation(project(":source:constant:persistence:repository"))
    implementation("org.springframework:spring-context")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}