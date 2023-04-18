dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:firm:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}