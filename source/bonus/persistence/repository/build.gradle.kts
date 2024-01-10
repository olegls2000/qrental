dependencies {
    implementation(project(":source:bonus:persistence:adapter"))
    implementation(project(":source:bonus:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("bonus-persistence-repository.jar")
}