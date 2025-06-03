dependencies {
    implementation(project(":source:billing:domain:bolt:persistence:adapter"))
    implementation(project(":source:billing:domain:bolt:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-bolt-persistence-repository.jar")
}