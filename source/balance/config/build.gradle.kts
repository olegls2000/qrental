dependencies {
    implementation(project(":source:balance:core"))
    implementation(project(":source:balance:api:in"))
    implementation(project(":source:balance:api:out"))
    implementation(project(":source:balance:persistence:flyway"))
    implementation(project(":source:balance:persistence:adapter"))
    implementation(project(":source:balance:persistence:repository"))

    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:driver:api:in"))

    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("balance-config.jar")
}