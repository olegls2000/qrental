dependencies {
    implementation(project(":source:common:api"))

    implementation(project(":source:queue:core"))
    implementation(project(":source:queue:api:in"))
    implementation(project(":source:queue:api:out"))
    implementation(project(":source:queue:persistence:adapter"))
    implementation(project(":source:queue:persistence:repository"))


    implementation("org.springframework:spring-context-support")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}
tasks.jar {
    archiveFileName.set("queue-config.jar")
}