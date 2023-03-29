plugins {
    id("q-java")
}

dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:callsign:api:out"))
    implementation(project(":source:callsign:domain"))
    implementation(project(":source:callsign:persistance:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}