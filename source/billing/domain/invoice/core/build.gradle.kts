dependencies{
    implementation(project(":source:common:api"))
    implementation(project(":source:common:utils"))
    implementation(project(":source:queue:api:in"))
    implementation(project(":source:billing:domain:invoice:api:in"))
    implementation(project(":source:billing:domain:invoice:api:out"))
    implementation(project(":source:billing:domain:invoice:domain"))

    implementation(project(":source:billing:domain:transaction:api:in"))
    implementation(project(":source:billing:domain:driver:api:in"))
    implementation(project(":source:billing:domain:firm:api:in"))
    implementation(project(":source:billing:domain:constant:api:in"))

    implementation(libs.q.jakarta.transaction)

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
    implementation(libs.q.jakarta.transaction)
    implementation(libs.q.librepdf.openpdf)
}

tasks.jar {
    archiveFileName.set("billing-invoice-core.jar")
}