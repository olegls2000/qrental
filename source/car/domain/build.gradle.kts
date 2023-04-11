plugins {
    id("q-java")
}

dependencies {
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}