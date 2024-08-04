dependencies {
    implementation(project(":source:insurance:persistence:adapter"))
    implementation(project(":source:insurance:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("insurance-persistence-repository.jar")
}