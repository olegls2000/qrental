plugins {
    id("q-java")
}

dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:car:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}