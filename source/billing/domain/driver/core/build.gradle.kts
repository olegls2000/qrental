dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:driver:api:in"))
    implementation(project(":source:billing:domain:bonus:api:in"))
    implementation(project(":source:billing:domain:insurance:api:in"))
    implementation(project(":source:billing:domain:driver:api:out"))
    implementation(project(":source:billing:domain:driver:domain"))
    implementation(project(":source:billing:domain:firm:api:in"))
    implementation(project(":source:billing:domain:constant:api:in"))
    implementation(project(":source:billing:domain:contract:api:in"))
    implementation(project(":source:common:utils"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)

    implementation(libs.q.jakarta.transaction)

    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation(project(":source:common:core"))
}

tasks.test {
    useJUnitPlatform()

    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}

tasks.jar {
    archiveFileName.set("billing-driver-core.jar")
}