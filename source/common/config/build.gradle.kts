dependencies {
    dependencies {
        implementation(project(":source:common:api"))
        implementation(project(":source:common:core"))
        implementation("org.springframework:spring-context-support")
        compileOnly(libs.q.lombok)
        annotationProcessor(libs.q.lombok)
    }
}

tasks.jar {
    archiveFileName.set("common-config.jar")
}