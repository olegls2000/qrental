dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:bonus:api:in"))
    implementation(project(":source:bonus:api:out"))
    implementation(project(":source:bonus:domain"))

    implementation(project(":source:car:api:in"))
    implementation(project(":source:contract:api:in"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:user:api:in"))
    implementation(project(":source:email:api:in"))
    implementation(project(":source:constant:api:in"))
    implementation(project(":source:transaction:api:in"))

    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")

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
    archiveFileName.set("bonus-core.jar")
}