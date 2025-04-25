import org.gradle.internal.impldep.org.joda.time.LocalDateTime
import org.gradle.internal.impldep.org.joda.time.format.DateTimeFormatter
import java.util.Date

dependencies {
    implementation(project(":source:queue:persistence:adapter"))
    implementation(project(":source:queue:persistence:entity"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    compileOnly(libs.q.lombok)
    annotationProcessor(libs.q.lombok)
}

tasks.jar {
    archiveFileName.set("queue-persistence-repository.jar")
}