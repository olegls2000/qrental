dependencies{
    implementation(project(":source:common"))
    implementation(project(":source:firm:api:in"))
    implementation(project(":source:firm:api:out"))
    implementation(project(":source:firm:domain"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}