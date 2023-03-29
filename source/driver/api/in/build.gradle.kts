plugins {
    id("q-java")
}

dependencies {
    //implementation(platform("ee.qrental:platform"))
    implementation(project(":source:common"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}