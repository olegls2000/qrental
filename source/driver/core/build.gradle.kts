plugins {
    id("q-java")
}

dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:driver:api:out"))
    implementation(project(":source:driver:domain"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}