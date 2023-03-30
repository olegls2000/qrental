plugins {
    id("q-java")
}

dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:invoice:api:out"))
    implementation(project(":source:invoice:domain"))
    implementation(project(":source:invoice:persistance:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}