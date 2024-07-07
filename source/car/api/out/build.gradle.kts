dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:car:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("car-api-out.jar")
}