dependencies {

    implementation(project(":source:common:api"))
    implementation(project(":source:queue:api:in"))
    implementation(project(":source:notification:domain:email:api:in"))

    implementation(project(":source:notification:task:api:in"))
    implementation(project(":source:notification:task:api:out"))
    implementation(project(":source:notification:task:domain"))
    implementation(project(":source:common:api"))

    implementation("org.springframework:spring-context-support")

    implementation(libs.q.jakarta.transaction)

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    archiveFileName.set("notification-task-core.jar")
}