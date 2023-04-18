dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:constant:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}