dependencies {
    implementation(project(":source:billing:domain:deposit:domain"))

    implementation(project(":source:common:api"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-deposit-api-out.jar")
}