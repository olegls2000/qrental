
val isGitPropertyPluginEnabledVar = System.getenv("GIT_PROPERTIES_GENERATION")
println("GIT_PROPERTIES_GENERATION: $isGitPropertyPluginEnabledVar")

val isGitPropertyPluginEnabled = System.getenv("GIT_PROPERTIES_GENERATION")?.toBoolean() ?: true
println("isGitPropertyPluginEnabled: $isGitPropertyPluginEnabled")

if (isGitPropertyPluginEnabled) {
    apply(plugin = "com.gorylenko.gradle-git-properties")
}

dependencies {
    implementation(project(":source:common:api"))
    implementation(project(":source:common:core"))
    implementation(project(":source:common:utils"))
    implementation(project(":source:notification:domain:email:api:in"))
    //TODO change to API:IN or Remove
    implementation(project(":source:notification:task:core"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.test {
    useJUnitPlatform()

    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}
tasks.jar {
    archiveFileName.set("notification-rest.jar")
}