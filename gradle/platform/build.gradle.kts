plugins{
    id("java-platform")
}

group = "ee.qrental"

javaPlatform.allowDependencies()
dependencies{
    api(platform("org.springframework.boot:spring-boot-dependencies:3.0.5"))
}

dependencies.constraints{
    api("org.projectlombok:lombok:1.18.26")
}