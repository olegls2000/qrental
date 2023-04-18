dependencies {
    implementation(project(":source:common"))
    api(project(":source:car:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}