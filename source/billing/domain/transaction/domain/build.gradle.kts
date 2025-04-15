dependencies {
    implementation(project(":source:common:utils"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}


tasks.jar {
    archiveFileName.set("billing-transaction-domain.jar")
}