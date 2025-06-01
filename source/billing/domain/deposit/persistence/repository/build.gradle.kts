dependencies {
    implementation(project(":source:billing:domain:deposit:persistence:adapter"))
    implementation(project(":source:billing:domain:deposit:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("org.postgresql:postgresql")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-deposit-persistence-repository.jar")
}