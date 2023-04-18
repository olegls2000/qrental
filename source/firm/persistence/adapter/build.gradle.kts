dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:firm:api:out"))
    implementation(project(":source:firm:domain"))
    implementation(project(":source:firm:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}