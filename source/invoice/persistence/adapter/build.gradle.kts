dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:invoice:api:out"))
    implementation(project(":source:invoice:domain"))
    implementation(project(":source:invoice:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("invoice-persistence-adapter.jar")
}