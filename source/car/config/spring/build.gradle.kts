plugins {
    id("q-java")
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation(project(":source:car:core"))
    implementation(project(":source:car:api:in"))
    implementation(project(":source:car:api:out"))
    implementation(project(":source:car:persistence:flyway"))
    implementation(project(":source:car:persistence:adapter"))
    implementation(project(":source:car:persistence:repository:spring"))
    implementation("org.springframework:spring-context")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}