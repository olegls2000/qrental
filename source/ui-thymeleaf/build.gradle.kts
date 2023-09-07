dependencies {
    implementation(project(":source:car:api:in"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:user:api:in"))
    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:invoice:api:in"))
    implementation(project(":source:firm:api:in"))
    implementation(project(":source:link:api:in"))
    implementation(project(":source:constant:api:in"))
    implementation(project(":source:common"))

    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.webjars:bootstrap:5.3.1")
    implementation("org.webjars:webjars-locator:0.47")
    implementation("org.webjars.npm:popper.js:1.16.1")
    implementation("org.webjars:jquery:3.7.1")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("ui-thymeleaf.jar")
}