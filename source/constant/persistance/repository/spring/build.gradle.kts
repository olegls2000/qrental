plugins {
    id("q-java")
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
}

dependencies {
    implementation(project(":source:firm:persistance:adapter"))
    implementation(project(":source:firm:persistance:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(project(mapOf("path" to ":source:constant:persistance:entity")))
    implementation(project(mapOf("path" to ":source:constant:persistance:adapter")))
    implementation(project(mapOf("path" to ":source:constant:persistance:adapter")))
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}