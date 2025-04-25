dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:driver:api:out"))
    implementation(project(":source:billing:domain:driver:domain"))
    implementation(project(":source:billing:domain:driver:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-driver-persistence-adapter.jar")
}