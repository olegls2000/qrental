
dependencies {
    implementation(project(":source:common"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("call-sign-api-in.jar")
}