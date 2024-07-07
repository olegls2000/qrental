dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:transaction:api:out"))
    implementation(project(":source:transaction:domain"))
    implementation(project(":source:transaction:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("transaction-persistence-adapter.jar")
}