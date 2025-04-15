dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:bonus:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-bonus-api-out.jar")
}