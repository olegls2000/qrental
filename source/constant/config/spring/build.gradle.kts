plugins {
    id("q-java")
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation(project(":source:constant:core"))
    implementation(project(":source:constant:api:in"))
    implementation(project(":source:constant:api:out"))
    implementation(project(":source:constant:persistence:flyway"))
    implementation(project(":source:constant:persistence:adapter"))
    implementation(project(":source:constant:persistence:repository:spring"))
    implementation("org.springframework:spring-context")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}