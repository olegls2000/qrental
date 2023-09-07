dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:user:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("user-api-out.jar")
}