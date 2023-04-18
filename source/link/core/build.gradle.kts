dependencies{
    implementation(project(":source:common"))
    implementation(project(":source:link:api:in"))
    implementation(project(":source:link:api:out"))
    implementation(project(":source:link:domain"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}