rootProject.name = "qrental"

pluginManagement {
    repositories.gradlePluginPortal()
    includeBuild("gradle/plugins")
}

dependencyResolutionManagement {
    repositories.mavenCentral()
    includeBuild("gradle/platform")
}

include("source:app")
include("source:common")

//Driver domain:
include("source:driver:api:in")
include("source:driver:api:out")
include("source:driver:domain")
include("source:driver:core")
include("source:driver:persistance:adapter")
include("source:driver:persistance:entity")
include("source:driver:persistance:repository:spring")
include("source:driver:persistance:flyway")
include("source:driver:config:spring")

include("source:ui-thymeleaf")