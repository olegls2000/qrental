dependencies {
    implementation(project(":source:billing:domain:insurance:core"))
    implementation(project(":source:billing:domain:insurance:api:in"))
    implementation(project(":source:billing:domain:insurance:api:out"))
    implementation(project(":source:billing:domain:insurance:persistence:flyway"))
    implementation(project(":source:billing:domain:insurance:persistence:adapter"))
    implementation(project(":source:billing:domain:insurance:persistence:repository"))

    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:constant:api:in"))
    implementation(project(":source:billing:domain:transaction:api:in"))
    implementation(project(":source:billing:domain:driver:api:in"))
    implementation(project(":source:billing:domain:car:api:in"))
    implementation(project(":source:billing:domain:contract:api:in"))
    implementation(project(":source:billing:domain:bolt:api:in"))

    implementation("org.springframework:spring-context-support")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-insurance-config.jar")
}