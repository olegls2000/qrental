plugins {
    id("q-java")
}

dependencies{
    implementation(project(":source:common"))
    implementation(project(":source:invoice:api:in"))
    implementation(project(":source:invoice:api:out"))
    implementation(project(":source:invoice:domain"))

    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:callsign:api:in"))
    implementation(project(":source:firm:api:in"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)

    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")
    implementation("com.github.librepdf:openpdf:1.3.30")
}