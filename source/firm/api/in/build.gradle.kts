dependencies {
    implementation(project(":source:common:api"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("firm-api-in.jar")
}