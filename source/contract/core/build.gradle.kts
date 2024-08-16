dependencies{
    implementation(project(":source:common:api"))
    implementation(project(":source:common:utils"))
    implementation(project(":source:contract:api:in"))
    implementation(project(":source:contract:api:out"))
    implementation(project(":source:contract:domain"))

    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:firm:api:in"))
    implementation(project(":source:email:api:in"))

    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.mockito:mockito-inline:5.2.0")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
    implementation(libs.q.jakarta.transaction)
    implementation(libs.q.librepdf.openpdf)
}

tasks.test {
    useJUnitPlatform()

    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}

tasks.jar {
    archiveFileName.set("contract-core.jar")
}