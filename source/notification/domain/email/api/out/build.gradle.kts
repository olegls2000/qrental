dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:notification:domain:email:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}
tasks.jar {
    archiveFileName.set("notification-email-api-out.jar")
}