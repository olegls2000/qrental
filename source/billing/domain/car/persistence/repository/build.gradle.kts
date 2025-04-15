dependencies {
    implementation(project(":source:billing:domain:car:persistence:adapter"))
    implementation(project(":source:billing:domain:car:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-car-persistence-repository.jar")
}