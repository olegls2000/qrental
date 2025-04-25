dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:queue:api:in"))

    implementation(project(":source:billing:domain:transaction:core"))
    implementation(project(":source:billing:domain:transaction:api:in"))
    implementation(project(":source:billing:domain:transaction:api:out"))
    implementation(project(":source:billing:domain:user:api:in"))
    implementation(project(":source:billing:domain:transaction:persistence:flyway"))
    implementation(project(":source:billing:domain:transaction:persistence:adapter"))
    implementation(project(":source:billing:domain:transaction:persistence:repository"))

    implementation(project(":source:billing:domain:driver:api:in"))
    implementation(project(":source:billing:domain:car:api:in"))
    implementation(project(":source:billing:domain:constant:api:in"))
    implementation(project(":source:billing:domain:contract:api:in"))

    implementation("org.springframework:spring-context-support")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-transaction-config.jar")
}