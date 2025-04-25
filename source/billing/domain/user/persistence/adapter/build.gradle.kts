dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:user:api:out"))
    implementation(project(":source:billing:domain:user:domain"))
    implementation(project(":source:billing:domain:user:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-user-persistence-adapter.jar")
}