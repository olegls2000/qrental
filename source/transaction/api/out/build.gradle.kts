plugins {
    id("q-java")
}

dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:transaction:domain"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}