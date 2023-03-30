plugins {
    id("q-java")
}

dependencies{
    implementation(project(":source:common"))
    implementation(project(":source:invoice:api:in"))
    implementation(project(":source:invoice:api:out"))
    implementation(project(":source:invoice:domain"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}