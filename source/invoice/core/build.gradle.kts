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

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}