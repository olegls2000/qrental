plugins {
    id("io.spring.dependency-management") version "1.1.0"
}
group = "ee.qrental"
subprojects {
    apply {
        plugin("java")
        plugin("java-library")
        plugin("io.spring.dependency-management")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.0.5")
        }
    }
}