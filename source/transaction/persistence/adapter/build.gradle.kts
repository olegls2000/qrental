plugins {
    id("q-java")
}

dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:transaction:api:out"))
    implementation(project(":source:transaction:domain"))
    implementation(project(":source:transaction:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}