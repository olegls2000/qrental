dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:car:api:out"))
    implementation(project(":source:billing:domain:car:domain"))
    implementation(project(":source:billing:domain:car:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}
tasks.jar {
    archiveFileName.set("billing-car-persistence-adapter.jar")
}