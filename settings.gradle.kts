rootProject.name = "qrental"

pluginManagement {
    repositories.gradlePluginPortal()
}

dependencyResolutionManagement {
    repositories.mavenCentral()
}

include("source:app")
include("source:common")

//Driver domain:
include("source:driver:api:in")
include("source:driver:api:out")
include("source:driver:domain")
include("source:driver:core")
include("source:driver:persistence:adapter")
include("source:driver:persistence:entity")
include("source:driver:persistence:repository")
include("source:driver:persistence:flyway")
include("source:driver:config")

//Car domain:
include("source:car:api:in")
include("source:car:api:out")
include("source:car:domain")
include("source:car:core")
include("source:car:persistence:adapter")
include("source:car:persistence:entity")
include("source:car:persistence:repository")
include("source:car:persistence:flyway")
include("source:car:config")

//Firm domain:
include("source:firm:api:in")
include("source:firm:api:out")
include("source:firm:domain")
include("source:firm:core")
include("source:firm:persistence:adapter")
include("source:firm:persistence:entity")
include("source:firm:persistence:repository")
include("source:firm:persistence:flyway")
include("source:firm:config")

//Link domain:
include("source:link:api:in")
include("source:link:api:out")
include("source:link:domain")
include("source:link:core")
include("source:link:persistence:adapter")
include("source:link:persistence:entity")
include("source:link:persistence:repository")
include("source:link:persistence:flyway")
include("source:link:config")

//Constant domain:
include("source:constant:api:in")
include("source:constant:api:out")
include("source:constant:domain")
include("source:constant:core")
include("source:constant:persistence:adapter")
include("source:constant:persistence:entity")
include("source:constant:persistence:repository")
include("source:constant:persistence:flyway")
include("source:constant:config")

//Call-sign domain:
include("source:callsign:api:in")
include("source:callsign:api:out")
include("source:callsign:domain")
include("source:callsign:core")
include("source:callsign:persistence:adapter")
include("source:callsign:persistence:entity")
include("source:callsign:persistence:repository")
include("source:callsign:persistence:flyway")
include("source:callsign:config")

//Invoice domain:
include("source:invoice:api:in")
include("source:invoice:api:out")
include("source:invoice:domain")
include("source:invoice:core")
include("source:invoice:persistence:adapter")
include("source:invoice:persistence:entity")
include("source:invoice:persistence:repository")
include("source:invoice:persistence:flyway")
include("source:invoice:config")

//Transaction domain:
include("source:transaction:api:in")
include("source:transaction:api:out")
include("source:transaction:domain")
include("source:transaction:core")
include("source:transaction:persistence:adapter")
include("source:transaction:persistence:entity")
include("source:transaction:persistence:repository")
include("source:transaction:persistence:flyway")
include("source:transaction:config")

//Email domain:
include("source:email:api:in")
include("source:email:core")
include("source:email:config")

include("source:ui-thymeleaf")