dependencies{
    implementation(project(":source:common"))
    implementation(project(":source:balance:api:in"))
    implementation(project(":source:balance:api:out"))
    implementation(project(":source:balance:domain"))

    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:driver:api:in"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
    implementation(libs.q.jakarta.transaction)
    implementation(libs.q.librepdf.openpdf)
}

tasks.jar {
    archiveFileName.set("balance-core.jar")
}