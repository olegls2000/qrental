dependencies{
    implementation(project(":source:common"))
    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:transaction:api:out"))
    implementation(project(":source:transaction:domain"))

    implementation(project(":source:driver:api:in"))
    implementation(project(":source:car:api:in"))
    implementation(project(":source:constant:api:in"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)

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
    archiveFileName.set("transaction-core.jar")
}