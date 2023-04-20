dependencies {
    implementation(project(":source:firm:core"))
    implementation(project(":source:firm:api:in"))
    implementation(project(":source:firm:api:out"))
    implementation(project(":source:firm:persistence:flyway"))
    implementation(project(":source:firm:persistence:adapter"))
    implementation(project(":source:firm:persistence:repository"))
    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("firm-config.jar")
}