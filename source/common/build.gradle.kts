plugins {
    id("q-java")
}

dependencies {
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
}

tasks.test {
    useJUnitPlatform()

    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}