dependencies {
    implementation(project(":source:common:core"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-contract-domain.jar")
}