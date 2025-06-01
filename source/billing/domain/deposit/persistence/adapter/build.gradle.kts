dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:deposit:api:out"))
    implementation(project(":source:billing:domain:deposit:domain"))
    implementation(project(":source:billing:domain:deposit:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-deposit-persistence-adapter.jar")
}