dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:user:api:out"))
    implementation(project(":source:user:domain"))
    implementation(project(":source:user:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("user-persistence-adapter.jar")
}