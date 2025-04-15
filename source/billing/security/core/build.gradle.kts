dependencies {
    implementation(project(":source:billing:security:api:in"))
    implementation(project(":source:billing:domain:user:api:in"))
    implementation(project(":source:common:api"))
    implementation("org.springframework.boot:spring-boot-starter-security")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-security-core.jar")
}