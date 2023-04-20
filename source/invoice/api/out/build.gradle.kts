dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:invoice:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("invoice-api-out.jar")
}