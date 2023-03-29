plugins {
    id("q-java")
}

dependencies{
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.0.5"))
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}