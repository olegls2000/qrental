dependencies {
    implementation(project(":source:link:core"))
    implementation(project(":source:link:api:in"))
    implementation(project(":source:link:api:out"))
    implementation(project(":source:link:persistence:flyway"))
    implementation(project(":source:link:persistence:adapter"))
    implementation(project(":source:link:persistence:repository"))
    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("link-config.jar")
}