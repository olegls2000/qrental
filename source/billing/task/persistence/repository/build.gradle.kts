dependencies {
    implementation(project(":source:billing:task:persistence:adapter"))
    implementation(project(":source:billing:task:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-task-persistence-repository.jar")
}