dependencies {
    implementation(project(":source:constant:persistence:adapter"))
    implementation(project(":source:constant:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}