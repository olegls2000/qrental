plugins {
    id("q-java")
}

dependencies {
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
    implementation(platform(libs.q.spring.boot))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()

    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}