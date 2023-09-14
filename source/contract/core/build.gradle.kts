dependencies{
    implementation(project(":source:common"))
    implementation(project(":source:contract:api:in"))
    implementation(project(":source:contract:api:out"))
    implementation(project(":source:contract:domain"))

    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:firm:api:in"))
    implementation(project(":source:email:api:in"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
    implementation(libs.q.jakarta.transaction)
    implementation(libs.q.librepdf.openpdf)
}

tasks.jar {
    archiveFileName.set("contract-core.jar")
}