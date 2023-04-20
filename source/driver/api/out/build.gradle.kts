dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:driver:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("driver-api-out.jar")
}