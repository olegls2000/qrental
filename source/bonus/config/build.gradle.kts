dependencies {
    implementation(project(":source:common:api"))

    implementation(project(":source:bonus:core"))
    implementation(project(":source:bonus:api:in"))

    implementation(project(":source:bonus:api:out"))
    implementation(project(":source:bonus:persistence:flyway"))
    implementation(project(":source:bonus:persistence:adapter"))
    implementation(project(":source:bonus:persistence:repository"))


    implementation(project(":source:constant:api:in"))
    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:email:api:in"))
    implementation(project(":source:car:api:in"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:user:api:in"))
    implementation(project(":source:contract:api:in"))
    implementation("org.springframework:spring-context-support")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("bonus-config.jar")
}