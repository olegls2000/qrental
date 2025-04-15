dependencies {
    dependencies {
        implementation(project(":source:billing:domain:user:api:in"))
        implementation(project(":source:billing:security:api:in"))
        implementation(project(":source:billing:security:core"))
        implementation("org.springframework:spring-context-support")
        implementation("org.springframework.boot:spring-boot-starter-security")
        compileOnly(libs.q.lombok)
        annotationProcessor(libs.q.lombok)
    }
}

tasks.jar {
    archiveFileName.set("billing-security-config.jar")
}