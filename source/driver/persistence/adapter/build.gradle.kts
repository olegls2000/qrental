dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:driver:api:out"))
    implementation(project(":source:driver:domain"))
    implementation(project(":source:driver:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("driver-persistence-adapter.jar")
}