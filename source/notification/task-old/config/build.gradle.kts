dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:queue:api:in"))

    implementation(project(":source:notification:domain:email:api:in"))
    implementation(project(":source:notification:domain:email:core"))
    implementation(project(":source:notification:task:core"))

    implementation("org.springframework:spring-context-support")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}
tasks.jar {
    archiveFileName.set("notification-task-config.jar")
}