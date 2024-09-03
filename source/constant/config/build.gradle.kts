dependencies {
    implementation(project(":source:constant:core"))
    implementation(project(":source:common:api"))
    implementation(project(":source:constant:api:in"))
    implementation(project(":source:constant:api:out"))
    implementation(project(":source:constant:persistence:flyway"))
    implementation(project(":source:constant:persistence:adapter"))
    implementation(project(":source:constant:persistence:repository"))
    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("constant-config.jar")
}