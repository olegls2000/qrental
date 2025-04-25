dependencies {
    implementation(project(":source:billing:domain:driver:core"))
    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:driver:api:in"))
    implementation(project(":source:billing:domain:insurance:api:in"))
    implementation(project(":source:billing:domain:driver:api:out"))
    implementation(project(":source:billing:domain:driver:persistence:flyway"))
    implementation(project(":source:billing:domain:driver:persistence:adapter"))
    implementation(project(":source:billing:domain:driver:persistence:repository"))
    implementation(project(":source:billing:domain:firm:api:in"))
    implementation(project(":source:billing:domain:contract:api:in"))
    implementation(project(":source:billing:domain:constant:api:in"))
    implementation(project(":source:billing:domain:bonus:api:in"))
    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-driver-config.jar")
}