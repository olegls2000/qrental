dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:driver:api:out"))
    implementation(project(":source:driver:domain"))
    implementation(project(":source:firm:api:in"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("driver-core.jar")
}