dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:contract:api:out"))
    implementation(project(":source:contract:domain"))
    implementation(project(":source:contract:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("contract-persistence-adapter.jar")
}