dependencies {

    implementation(project(":source:common:api"))

    implementation(project(":source:queue:api:out"))
    implementation(project(":source:queue:domain"))
    implementation(project(":source:queue:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("queue-persistence-adapter.jar")
}