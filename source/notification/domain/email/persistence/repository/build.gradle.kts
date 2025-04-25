dependencies {
    implementation(project(":source:notification:domain:email:persistence:adapter"))
    implementation(project(":source:notification:domain:email:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}
tasks.jar {
    archiveFileName.set("notification-email-persistence-repository.jar")
}