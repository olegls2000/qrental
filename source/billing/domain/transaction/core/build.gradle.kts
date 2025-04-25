dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:common:utils"))

    implementation(project(":source:queue:api:in"))
    implementation(project(":source:billing:domain:transaction:api:in"))
    implementation(project(":source:billing:domain:transaction:api:out"))
    implementation(project(":source:billing:domain:transaction:domain"))

    implementation(project(":source:billing:domain:driver:api:in"))
    implementation(project(":source:billing:domain:contract:api:in"))
    implementation(project(":source:billing:domain:car:api:in"))
    implementation(project(":source:billing:domain:constant:api:in"))
    implementation(project(":source:billing:domain:user:api:in"))
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
    archiveFileName.set("billing-transaction-core.jar")
}