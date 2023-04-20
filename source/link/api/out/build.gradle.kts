dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:link:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}


tasks.jar {
    archiveFileName.set("link-api-out.jar")
}