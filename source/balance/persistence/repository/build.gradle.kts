dependencies {
    implementation(project(":source:balance:persistence:adapter"))
    implementation(project(":source:balance:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("balance-persistence-repository.jar")
}