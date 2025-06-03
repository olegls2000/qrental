dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:bolt:api:out"))
    implementation(project(":source:billing:domain:bolt:domain"))
    implementation(project(":source:billing:domain:bolt:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-bolt-persistence-adapter.jar")
}