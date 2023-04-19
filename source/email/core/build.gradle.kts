dependencies {
    implementation(project(":source:email:api:in"))
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}