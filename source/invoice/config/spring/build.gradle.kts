plugins {
    id("q-java")
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation(project(":source:invoice:core"))
    implementation(project(":source:invoice:api:in"))
    implementation(project(":source:invoice:api:out"))
    implementation(project(":source:invoice:persistance:flyway"))
    implementation(project(":source:invoice:persistance:adapter"))
    implementation(project(":source:invoice:persistance:repository:spring"))
    implementation("org.springframework:spring-context")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}