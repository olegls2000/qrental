plugins {
    id("q-java")
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation(project(":source:firm:core"))
    implementation(project(":source:firm:api:in"))
    implementation(project(":source:firm:api:out"))
    implementation(project(":source:firm:persistance:flyway"))
    implementation(project(":source:firm:persistance:adapter"))
    implementation(project(":source:firm:persistance:repository:spring"))
    implementation("org.springframework:spring-context")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}