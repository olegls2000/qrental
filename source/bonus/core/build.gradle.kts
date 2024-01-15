dependencies{
    implementation(project(":source:common"))
    implementation(project(":source:bonus:api:in"))
    implementation(project(":source:bonus:api:out"))
    implementation(project(":source:bonus:domain"))

    implementation(project(":source:car:api:in"))
    implementation(project(":source:user:api:in"))
    implementation(project(":source:email:api:in"))
    implementation(project(":source:constant:api:in"))
    implementation(project(":source:transaction:api:in"))

    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("bonus-core.jar")
}