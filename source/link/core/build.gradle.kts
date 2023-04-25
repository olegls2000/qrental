dependencies{
    implementation(project(":source:common"))
    implementation(project(":source:link:api:in"))
    implementation(project(":source:link:api:out"))
    implementation(project(":source:link:domain"))
    implementation(project(mapOf("path" to ":source:callsign:api:in")))
    implementation(project(mapOf("path" to ":source:driver:api:in")))
    implementation(project(mapOf("path" to ":source:car:api:in")))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("link-core.jar")
}