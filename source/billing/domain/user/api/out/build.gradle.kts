dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:user:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-user-api-out.jar")
}