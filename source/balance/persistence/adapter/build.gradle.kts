dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:balance:api:out"))
    implementation(project(":source:balance:domain"))
    implementation(project(":source:balance:persistence:entity"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("balance-persistence-adapter.jar")
}