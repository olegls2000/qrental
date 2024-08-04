dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:common:utils"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("insurance-api-in.jar")
}