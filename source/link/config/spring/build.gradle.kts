plugins {
    id("q-java")
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation(project(":source:link:core"))
    implementation(project(":source:link:api:in"))
    implementation(project(":source:link:api:out"))
    implementation(project(":source:link:persistance:flyway"))
    implementation(project(":source:link:persistance:adapter"))
    implementation(project(":source:link:persistance:repository:spring"))
    implementation("org.springframework:spring-context")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}