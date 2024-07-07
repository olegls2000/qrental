dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:car:api:out"))
    implementation(project(":source:car:domain"))
    implementation(project(":source:car:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}
tasks.jar {
    archiveFileName.set("car-persistence-adapter.jar")
}