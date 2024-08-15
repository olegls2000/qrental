dependencies {
    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:bonus:api:in"))
    implementation(project(":source:insurance:api:in"))
    implementation(project(":source:constant:api:in"))
    implementation(project(":source:common:api"))
    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    archiveFileName.set("task.jar")
}