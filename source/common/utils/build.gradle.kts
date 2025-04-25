dependencies {
    dependencies {
        compileOnly(libs.q.lombok)
        annotationProcessor(libs.q.lombok)
        implementation("org.threeten:threeten-extra:1.8.0")
        testImplementation("org.junit.jupiter:junit-jupiter-engine")
    }
}

tasks.test {
    useJUnitPlatform()

    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}

tasks.jar {
    archiveFileName.set("common-utils.jar")
}