dependencies {
    dependencies {
        compileOnly(libs.q.lombok)
        annotationProcessor(libs.q.lombok)
        testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    }
}

tasks.jar {
    archiveFileName.set("common-utils.jar")
}