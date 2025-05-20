dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:transaction:api:in"))
    implementation(project(":source:billing:domain:driver:api:in"))
    implementation(project(":source:billing:domain:bonus:api:in"))
    implementation(project(":source:billing:domain:insurance:api:in"))
    implementation(project(":source:billing:domain:constant:api:in"))
    implementation(project(":source:billing:domain:report:api:in"))
    implementation(project(":source:billing:task:core"))
    implementation(project(":source:queue:api:in"))

    implementation("org.springframework:spring-context-support")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}
tasks.jar {
    archiveFileName.set("billing-task-config.jar")
}