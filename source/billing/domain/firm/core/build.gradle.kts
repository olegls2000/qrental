dependencies{
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:firm:api:in"))
    implementation(project(":source:billing:domain:firm:api:out"))
    implementation(project(":source:billing:domain:firm:domain"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-firm-core.jar")
}