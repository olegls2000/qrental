dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:car:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-car-api-out.jar")
}