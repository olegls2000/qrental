dependencies {
    implementation(project(":source:billing:domain:car:core"))
    implementation(project(":source:billing:domain:car:api:in"))
    implementation(project(":source:billing:domain:car:api:out"))
    implementation(project(":source:billing:domain:car:persistence:flyway"))
    implementation(project(":source:billing:domain:car:persistence:adapter"))
    implementation(project(":source:billing:domain:car:persistence:repository"))

    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:driver:api:in"))
    implementation(project(":source:billing:domain:constant:api:in"))
    implementation(project(":source:queue:api:in"))

    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-car-config.jar")
}
