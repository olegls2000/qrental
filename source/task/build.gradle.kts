dependencies {
    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:common"))
    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("task.jar")
}