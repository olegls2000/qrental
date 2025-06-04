dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:bolt:core"))
    implementation(project(":source:billing:domain:driver:api:in"))
    implementation(project(":source:billing:domain:constant:api:in"))
    implementation(project(":source:billing:domain:bolt:api:in"))
    implementation(project(":source:billing:domain:bolt:api:out"))
    implementation(project(":source:billing:domain:bolt:persistence:flyway"))
    implementation(project(":source:billing:domain:bolt:persistence:adapter"))
    implementation(project(":source:billing:domain:bolt:persistence:repository"))
    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-bolt-config.jar")
}