dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:notification:task:domain"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("notification-task-api-out.jar")
}