dependencies{
    implementation(project(":source:common"))
    implementation(project(":source:constant:api:in"))
    implementation(project(":source:constant:api:out"))
    implementation(project(":source:constant:domain"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("constant-core.jar")
}