plugins {
    id("q-java")
}

dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:link:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}