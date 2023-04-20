dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:callsign:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("call-sign-api-out.jar")
}