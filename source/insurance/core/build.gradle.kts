dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:common:utils"))
    implementation(project(":source:insurance:api:in"))
    implementation(project(":source:insurance:api:out"))
    implementation(project(":source:insurance:domain"))

    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:constant:api:in"))
    implementation(project(":source:car:api:in"))

    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
    implementation(libs.q.jakarta.transaction)
    implementation(libs.q.librepdf.openpdf)

    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.mockito:mockito-inline:5.2.0")
}

tasks.test {
    useJUnitPlatform()

    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}

tasks.jar {
    archiveFileName.set("insurance-core.jar")
}