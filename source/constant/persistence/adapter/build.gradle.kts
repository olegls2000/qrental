dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:constant:api:out"))
    implementation(project(":source:constant:domain"))
    implementation(project(":source:constant:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("constant-persistence-adapter.jar")
}