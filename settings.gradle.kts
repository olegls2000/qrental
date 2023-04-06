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

//Firm domain:
include("source:firm:api:in")
include("source:firm:api:out")
include("source:firm:domain")
include("source:firm:core")
include("source:firm:persistance:adapter")
include("source:firm:persistance:entity")
include("source:firm:persistance:repository:spring")
include("source:firm:persistance:flyway")
include("source:firm:config:spring")


//Call-sign domain:
include("source:callsign:api:in")
include("source:callsign:api:out")
include("source:callsign:domain")
include("source:callsign:core")
include("source:callsign:persistance:adapter")
include("source:callsign:persistance:entity")
include("source:callsign:persistance:repository:spring")
include("source:callsign:persistance:flyway")
include("source:callsign:config:spring")

//Invoice domain:
include("source:invoice:api:in")
include("source:invoice:api:out")
include("source:invoice:domain")
include("source:invoice:core")
include("source:invoice:persistance:adapter")
include("source:invoice:persistance:entity")
include("source:invoice:persistance:repository:spring")
include("source:invoice:persistance:flyway")
include("source:invoice:config:spring")

//Transaction domain:
include("source:transaction:api:in")
include("source:transaction:api:out")
include("source:transaction:domain")
include("source:transaction:core")
include("source:transaction:persistance:adapter")
include("source:transaction:persistance:entity")
include("source:transaction:persistance:repository:spring")
include("source:transaction:persistance:flyway")
include("source:transaction:config:spring")

include("source:ui-thymeleaf")