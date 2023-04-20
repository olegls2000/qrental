dependencies {
    implementation(project(":source:callsign:persistence:adapter"))
    implementation(project(":source:callsign:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("call-sign-persistence-repository.jar")
}