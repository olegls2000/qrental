dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:constant:api:in"))
    implementation(project(":source:billing:domain:car:api:in"))
    implementation(project(":source:billing:domain:car:api:out"))
    implementation(project(":source:billing:domain:car:domain"))
    implementation(project(":source:billing:domain:driver:api:in"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)

    implementation(libs.q.jakarta.transaction)
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
    archiveFileName.set("billing-car-core.jar")
}