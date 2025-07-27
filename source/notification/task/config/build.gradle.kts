dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:notification:domain:email:api:in"))
    implementation(project(":source:notification:task:api:in"))
    implementation(project(":source:notification:task:api:out"))
    implementation(project(":source:notification:task:persistence:adapter"))
    implementation(project(":source:notification:task:persistence:repository"))
    implementation(project(":source:notification:task:core"))
    implementation(project(":source:queue:api:in"))

    implementation("org.springframework:spring-context-support")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}
tasks.jar {
    archiveFileName.set("notification-task-config.jar")
}