dependencies {
    implementation(project(":source:billing:domain:constant:core"))
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:constant:api:in"))
    implementation(project(":source:billing:domain:constant:api:out"))
    implementation(project(":source:billing:domain:constant:persistence:flyway"))
    implementation(project(":source:billing:domain:constant:persistence:adapter"))
    implementation(project(":source:billing:domain:constant:persistence:repository"))
    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-constant-config.jar")
}