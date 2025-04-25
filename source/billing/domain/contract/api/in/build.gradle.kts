dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:common:core"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-contract-api-in.jar")
}