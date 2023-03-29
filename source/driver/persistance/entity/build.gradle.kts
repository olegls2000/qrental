plugins {
    id("q-java")
}

dependencies{
    implementation(libs.q.jakarta)
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}