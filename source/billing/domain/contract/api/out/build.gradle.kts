dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:contract:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-contract-api-out.jar")
}