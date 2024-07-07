dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:bonus:api:out"))
    implementation(project(":source:bonus:domain"))
    implementation(project(":source:bonus:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("bonus-persistence-adapter.jar")
}