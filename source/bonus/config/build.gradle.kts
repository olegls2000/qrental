dependencies {
    implementation(project(":source:bonus:core"))
    implementation(project(":source:bonus:api:in"))
    implementation(project(":source:bonus:api:out"))
    implementation(project(":source:bonus:persistence:flyway"))
    implementation(project(":source:bonus:persistence:adapter"))
    implementation(project(":source:bonus:persistence:repository"))
    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("bonus-config.jar")
}