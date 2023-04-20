dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:callsign:api:out"))
    implementation(project(":source:callsign:domain"))
    implementation(project(":source:callsign:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("call-sign-persistence-adapter.jar")
}