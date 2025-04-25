dependencies{
    implementation(libs.q.jakarta.persistence)
    implementation(libs.q.hibernate.envers)
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-car-persistence-entity.jar")
}