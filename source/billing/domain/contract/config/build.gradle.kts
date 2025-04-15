dependencies {
    implementation(project(":source:billing:domain:contract:core"))
    implementation(project(":source:billing:domain:contract:api:in"))
    implementation(project(":source:billing:domain:contract:api:out"))
    implementation(project(":source:billing:domain:contract:persistence:flyway"))
    implementation(project(":source:billing:domain:contract:persistence:adapter"))
    implementation(project(":source:billing:domain:contract:persistence:repository"))

    implementation(project(":source:common:api"))
    implementation(project(":source:queue:api:in"))
    implementation(project(":source:billing:domain:transaction:api:in"))
    implementation(project(":source:billing:domain:constant:api:in"))
    implementation(project(":source:billing:domain:driver:api:in"))
    implementation(project(":source:billing:domain:insurance:api:in"))
    implementation(project(":source:billing:domain:firm:api:in"))

    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-contract-config.jar")
}