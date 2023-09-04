dependencies {
    implementation(project(":source:user:core"))
    implementation(project(":source:user:api:in"))
    implementation(project(":source:user:api:out"))
    implementation(project(":source:user:persistence:flyway"))
    implementation(project(":source:user:persistence:adapter"))
    implementation(project(":source:user:persistence:repository"))
    implementation(project(":source:firm:api:in"))
    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("user-config.jar")
}