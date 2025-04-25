dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:notification:domain:email:api:in"))
    implementation(project(":source:notification:domain:email:api:out"))
    implementation(project(":source:notification:domain:email:domain"))

    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-mail")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}
tasks.jar {
    archiveFileName.set("notification-email-core.jar")
}