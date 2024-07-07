dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:constant:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}
tasks.jar {
    archiveFileName.set("constant-api-out.jar")
}