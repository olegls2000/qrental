dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:insurance:api:out"))
    implementation(project(":source:billing:domain:insurance:domain"))
    implementation(project(":source:billing:domain:insurance:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-insurance-persistence-adapter.jar")
}