dependencies{
    implementation(libs.q.jakarta.persistence)
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("invoice-persistence-entity.jar")
}