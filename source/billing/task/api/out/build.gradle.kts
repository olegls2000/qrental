dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:task:domain"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-task-api-out.jar")
}