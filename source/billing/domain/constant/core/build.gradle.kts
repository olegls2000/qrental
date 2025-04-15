dependencies{
    implementation(project(":source:common:api"))
    implementation(project(":source:common:utils"))
    implementation(project(":source:billing:domain:constant:api:in"))
    implementation(project(":source:billing:domain:constant:api:out"))
    implementation(project(":source:billing:domain:constant:domain"))
    implementation("org.threeten:threeten-extra:1.8.0")

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
    archiveFileName.set("billing-constant-core.jar")
}