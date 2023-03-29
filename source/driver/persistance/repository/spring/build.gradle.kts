plugins {
    id("q-java")
    id("org.springframework.boot") version "3.0.5"
}

dependencies{
    implementation(project(":source:driver:persistance:adapter"))
    implementation(project(":source:driver:persistance:entity"))
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.0.5"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
    runtimeOnly("org.postgresql:postgresql")
}