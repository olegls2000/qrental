dependencies {
    implementation(project(":source:common"))
    implementation(project(":source:balance:domain"))
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("balance-api-out.jar")
}