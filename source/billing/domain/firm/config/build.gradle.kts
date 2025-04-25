dependencies {
    implementation(project(":source:billing:domain:firm:core"))
    implementation(project(":source:billing:domain:firm:api:in"))
    implementation(project(":source:billing:domain:firm:api:out"))
    implementation(project(":source:billing:domain:firm:persistence:flyway"))
    implementation(project(":source:billing:domain:firm:persistence:adapter"))
    implementation(project(":source:billing:domain:firm:persistence:repository"))
    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-firm-config.jar")
}