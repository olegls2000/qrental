dependencies {
    implementation(project(":source:billing:domain:deposit:core"))
    implementation(project(":source:billing:domain:deposit:api:in"))
    implementation(project(":source:billing:domain:deposit:api:out"))
    implementation(project(":source:billing:domain:deposit:persistence:flyway"))
    implementation(project(":source:billing:domain:deposit:persistence:adapter"))
    implementation(project(":source:billing:domain:deposit:persistence:repository"))

    implementation(project(":source:common:api"))
    implementation(project(":source:billing:domain:driver:api:in"))

    implementation("org.springframework:spring-context-support")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("billing-deposit-config.jar")
}