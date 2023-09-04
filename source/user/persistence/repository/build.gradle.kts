dependencies {
    implementation(project(":source:user:persistence:adapter"))
    implementation(project(":source:user:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("user-persistence-repository.jar")
}