dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:constant:api:out"))
    implementation(project(":source:billing:domain:constant:domain"))
    implementation(project(":source:billing:domain:constant:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-constant-persistence-adapter.jar")
}