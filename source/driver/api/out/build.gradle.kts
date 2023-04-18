dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:driver:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}