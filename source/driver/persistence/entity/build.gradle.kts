dependencies{
    implementation(libs.q.jakarta.persistence)
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("driver-persistence-entity.jar")
}