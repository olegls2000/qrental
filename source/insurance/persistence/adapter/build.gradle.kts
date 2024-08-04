dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:insurance:api:out"))
    implementation(project(":source:insurance:domain"))
    implementation(project(":source:insurance:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("insurance-persistence-adapter.jar")
}