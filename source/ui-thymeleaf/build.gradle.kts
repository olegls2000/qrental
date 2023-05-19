dependencies {
    implementation(project(":source:car:api:in"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:balance:api:in"))
    implementation(project(":source:invoice:api:in"))
    implementation(project(":source:firm:api:in"))
    implementation(project(":source:link:api:in"))
    implementation(project(":source:constant:api:in"))
    implementation(project(":source:common"))

    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("ui-thymeleaf.jar")
}