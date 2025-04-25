dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:queue:api:in"))

    implementation(project(":source:billing:domain:bonus:core"))
    implementation(project(":source:billing:domain:bonus:api:in"))

    implementation(project(":source:billing:domain:bonus:api:out"))
    implementation(project(":source:billing:domain:bonus:persistence:flyway"))
    implementation(project(":source:billing:domain:bonus:persistence:adapter"))
    implementation(project(":source:billing:domain:bonus:persistence:repository"))


    implementation(project(":source:billing:domain:constant:api:in"))
    implementation(project(":source:billing:domain:transaction:api:in"))
    implementation(project(":source:billing:domain:car:api:in"))
    implementation(project(":source:billing:domain:driver:api:in"))
    implementation(project(":source:billing:domain:user:api:in"))
    implementation(project(":source:billing:domain:contract:api:in"))
    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-bonus-config.jar")
}