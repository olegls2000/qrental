dependencies {
    implementation(project(":source:firm:persistence:adapter"))
    implementation(project(":source:firm:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}