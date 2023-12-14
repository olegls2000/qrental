dependencies{
    implementation(project(":source:common"))
    implementation(project(":source:invoice:api:in"))
    implementation(project(":source:invoice:api:out"))
    implementation(project(":source:invoice:domain"))

    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:firm:api:in"))
    implementation(project(":source:constant:api:in"))
    implementation(project(":source:email:api:in"))

    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
    implementation(libs.q.jakarta.transaction)
    implementation(libs.q.librepdf.openpdf)
}

tasks.jar {
    archiveFileName.set("invoice-core.jar")
}