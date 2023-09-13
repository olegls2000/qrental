dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:contract:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("contract-api-out.jar")
}