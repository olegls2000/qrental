dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:queue:api:in"))

    implementation(project(":source:billing:domain:user:core"))
    implementation(project(":source:billing:domain:user:api:in"))
    implementation(project(":source:billing:domain:user:api:out"))
    implementation(project(":source:billing:domain:user:persistence:flyway"))
    implementation(project(":source:billing:domain:user:persistence:adapter"))
    implementation(project(":source:billing:domain:user:persistence:repository"))
    implementation(project(":source:billing:domain:firm:api:in"))
    implementation(project(":source:billing:security:api:in"))
    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-user-config.jar")
}