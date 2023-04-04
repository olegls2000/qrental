plugins {
    id("q-java")
}

dependencies{
    implementation(project(":source:common"))
    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:transaction:api:out"))
    implementation(project(":source:transaction:domain"))

    implementation(project(":source:driver:api:in"))
    implementation(project(":source:callsign:api:in"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}