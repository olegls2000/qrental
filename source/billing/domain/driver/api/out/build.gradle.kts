dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:driver:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-driver-api-out.jar")
}