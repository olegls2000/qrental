dependencies{
    implementation(project(":source:billing:domain:deposit:api:in"))
    implementation(project(":source:billing:domain:deposit:api:out"))
    implementation(project(":source:billing:domain:deposit:domain"))

    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:driver:api:in"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-deposit-core.jar")
}