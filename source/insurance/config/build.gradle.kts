dependencies {
    implementation(project(":source:insurance:core"))
    implementation(project(":source:insurance:api:in"))
    implementation(project(":source:insurance:api:out"))
    implementation(project(":source:insurance:persistence:flyway"))
    implementation(project(":source:insurance:persistence:adapter"))
    implementation(project(":source:insurance:persistence:repository"))

    implementation(project(":source:common:api"))
    implementation(project(":source:transaction:api:in"))
    implementation(project(":source:driver:api:in"))
    implementation(project(":source:car:api:in"))

    implementation("org.springframework:spring-context-support")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("insurance-config.jar")
}