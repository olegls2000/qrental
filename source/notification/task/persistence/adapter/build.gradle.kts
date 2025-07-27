dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:notification:task:api:out"))
    implementation(project(":source:notification:task:domain"))
    implementation(project(":source:notification:task:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("notification-task-persistence-adapter.jar")
}