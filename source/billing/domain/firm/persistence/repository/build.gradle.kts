dependencies {
    implementation(project(":source:billing:domain:firm:persistence:adapter"))
    implementation(project(":source:billing:domain:firm:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-firm-persistence-repository.jar")
}