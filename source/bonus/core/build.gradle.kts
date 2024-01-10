dependencies{
    implementation(project(":source:common"))
    implementation(project(":source:bonus:api:in"))
    implementation(project(":source:bonus:api:out"))
    implementation(project(":source:bonus:domain"))

    implementation(project(":source:car:api:in"))
    implementation(project(":source:constant:api:in"))
    implementation(project(":source:transaction:api:in"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("bonus-core.jar")
}