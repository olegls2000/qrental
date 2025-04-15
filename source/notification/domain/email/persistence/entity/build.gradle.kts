dependencies {
    implementation(libs.q.hypersistence.utils.hibernate)
    implementation(libs.q.jakarta.persistence)
    implementation(libs.q.hibernate.envers)
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}
tasks.jar {
    archiveFileName.set("notification-email-persistence-entity.jar")
}