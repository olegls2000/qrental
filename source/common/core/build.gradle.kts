dependencies {
    dependencies {
        implementation(project(":source:common:api"))
        compileOnly(libs.q.lombok)
        annotationProcessor(libs.q.lombok)

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
    archiveFileName.set("common-core.jar")
}