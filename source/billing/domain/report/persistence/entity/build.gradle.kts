dependencies{
    implementation(libs.q.hypersistence.utils.hibernate)
    implementation(libs.q.hibernate.envers)
    implementation(libs.q.jakarta.persistence)
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-report-persistence-entity.jar")
}
