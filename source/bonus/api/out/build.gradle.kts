dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:bonus:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("bonus-api-out.jar")
}