plugins {
    id("q-java")
}

dependencies{
    implementation(project(":source:common"))
    implementation(project(":source:callsign:api:in"))
    implementation(project(":source:callsign:api:out"))
    implementation(project(":source:callsign:domain"))

    implementation(project(":source:driver:api:in"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}