dependencies {
    dependencies {
        implementation(project(":source:email:api:in"))
        implementation(project(":source:email:core"))
        implementation("org.springframework:spring-context-support")
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
        implementation("org.springframework.boot:spring-boot-starter-mail")
        compileOnly(libs.q.lombok)
        annotationProcessor(libs.q.lombok)
    }
}

tasks.jar {
    archiveFileName.set("email-config.jar")
}