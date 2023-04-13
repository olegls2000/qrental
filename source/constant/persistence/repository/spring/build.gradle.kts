plugins {
    id("q-java")
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation(project(":source:firm:persistence:adapter"))
    implementation(project(":source:firm:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(project(mapOf("path" to ":source:constant:persistence:entity")))
    implementation(project(mapOf("path" to ":source:constant:persistence:adapter")))
    implementation(project(mapOf("path" to ":source:constant:persistence:adapter")))
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}