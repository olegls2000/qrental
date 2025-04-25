dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:invoice:api:out"))
    implementation(project(":source:billing:domain:invoice:domain"))
    implementation(project(":source:billing:domain:invoice:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-invoice-persistence-adapter.jar")
}