dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:link:api:out"))
    implementation(project(":source:link:domain"))
    implementation(project(":source:link:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("link-persistence-adapter.jar")
}