dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:user:api:in"))
    implementation(project(":source:user:api:out"))
    implementation(project(":source:user:domain"))

    implementation(project(":source:email:api:in"))

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
    implementation(libs.q.jakarta.transaction)
}

tasks.jar {
    archiveFileName.set("user-core.jar")
}