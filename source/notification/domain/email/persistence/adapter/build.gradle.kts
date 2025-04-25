dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:notification:domain:email:api:out"))
    implementation(project(":source:notification:domain:email:domain"))
    implementation(project(":source:notification:domain:email:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}
tasks.jar {
    archiveFileName.set("notification-email-persistence-adapter.jar")
}