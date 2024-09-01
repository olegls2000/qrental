dependencies {
    implementation(project(":source:driver:core"))
    implementation(project(":source:common:api"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:driver:api:out"))
    implementation(project(":source:driver:persistence:flyway"))
    implementation(project(":source:driver:persistence:adapter"))
    implementation(project(":source:driver:persistence:repository"))
    implementation(project(":source:firm:api:in"))
    implementation(project(":source:contract:api:in"))
    implementation(project(":source:constant:api:in"))
    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("driver-config.jar")
}