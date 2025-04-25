dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:notification:domain:email:api:in"))
    implementation(project(":source:notification:domain:email:core"))
    implementation(project(":source:notification:domain:email:domain"))
    implementation(project(":source:notification:domain:email:persistence:adapter"))
    implementation(project(":source:notification:domain:email:persistence:repository"))

    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework:spring-context-support")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}
tasks.jar {
    archiveFileName.set("notification-email-config.jar")
}