dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:firm:api:out"))
    implementation(project(":source:billing:domain:firm:domain"))
    implementation(project(":source:billing:domain:firm:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-firm-persistence-adapter.jar")
}