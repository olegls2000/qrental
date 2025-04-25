dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:queue:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}
tasks.jar {
    archiveFileName.set("queue-api-out.jar")
}