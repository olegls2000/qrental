plugins {
    id("q-java")
}

dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:link:api:out"))
    implementation(project(":source:link:domain"))
    implementation(project(":source:link:persistance:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}