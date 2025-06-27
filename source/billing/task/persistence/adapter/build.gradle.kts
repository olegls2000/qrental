dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:task:api:out"))
    implementation(project(":source:billing:task:domain"))
    implementation(project(":source:billing:task:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-task-persistence-adapter.jar")
}