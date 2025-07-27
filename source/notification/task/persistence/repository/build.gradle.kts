dependencies {
    implementation(project(":source:notification:task:persistence:adapter"))
    implementation(project(":source:notification:task:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("notification-task-persistence-repository.jar")
}