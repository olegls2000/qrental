plugins {
    id("q-java")
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:callsign:api:in"))
    implementation(project(":source:common"))

    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}