dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:transaction:api:out"))
    implementation(project(":source:transaction:domain"))
    implementation(project(":source:transaction:persistence:entity"))

    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.mockito:mockito-inline:5.2.0")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.test {
    useJUnitPlatform()

    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}

tasks.jar {
    archiveFileName.set("transaction-persistence-adapter.jar")
}