dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:queue:api:in"))

    implementation(project(":source:billing:domain:report:core"))
    implementation(project(":source:billing:domain:report:api:in"))
    implementation(project(":source:billing:domain:report:api:out"))
    implementation(project(":source:billing:domain:report:persistence:flyway"))
    implementation(project(":source:billing:domain:report:persistence:adapter"))
    implementation(project(":source:billing:domain:report:persistence:repository"))

    implementation(project(":source:billing:domain:transaction:api:in"))
    implementation(project(":source:billing:domain:driver:api:in"))
    implementation(project(":source:billing:domain:constant:api:in"))
    implementation(project(":source:billing:domain:car:api:in"))
    implementation(project(":source:billing:domain:firm:api:in"))
    implementation(project(":source:billing:domain:bonus:api:in"))
    implementation(project(":source:billing:domain:contract:api:in"))
    implementation(project(":source:billing:domain:deposit:api:in"))
    implementation(project(":source:billing:domain:insurance:api:in"))

    implementation("org.springframework:spring-context-support")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-report-config.jar")
}
