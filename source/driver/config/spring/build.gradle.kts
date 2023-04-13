plugins {
    id("q-java")
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation(project(":source:driver:core"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:driver:api:out"))
    implementation(project(":source:driver:persistence:flyway"))
    implementation(project(":source:driver:persistence:adapter"))
    implementation(project(":source:driver:persistence:repository:spring"))
    implementation("org.springframework:spring-context")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}