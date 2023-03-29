plugins {
    id("q-java")
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation(project(":source:callsign:core"))
    implementation(project(":source:callsign:api:in"))
    implementation(project(":source:callsign:api:out"))
    implementation(project(":source:callsign:persistance:flyway"))
    implementation(project(":source:callsign:persistance:adapter"))
    implementation(project(":source:callsign:persistance:repository:spring"))

    implementation(project(":source:driver:api:in"))
    implementation("org.springframework:spring-context")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}