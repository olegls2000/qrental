plugins {
    id("q-java")
}

dependencies{
    implementation(libs.q.jakarta.persistence)
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}