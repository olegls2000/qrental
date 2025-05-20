dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:report:api:out"))
    implementation(project(":source:billing:domain:report:domain"))
    implementation(project(":source:billing:domain:report:persistence:entity"))
    implementation(project(":source:billing:domain:driver:api:in"))
    implementation(project(":source:billing:domain:constant:api:in"))
    implementation(project(":source:billing:domain:car:api:in"))
    implementation(project(":source:billing:domain:firm:api:in"))
    implementation(project(":source:billing:domain:bonus:api:in"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-report-persistence-adapter.jar")
}