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

//Contract domain:
include("source:contract:api:in")
include("source:contract:api:out")
include("source:contract:domain")
include("source:contract:core")
include("source:contract:persistence:adapter")
include("source:contract:persistence:entity")
include("source:contract:persistence:repository")
include("source:contract:persistence:flyway")
include("source:contract:config")

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

//User domain:
include("source:user:api:in")
include("source:user:api:out")
include("source:user:domain")
include("source:user:core")
include("source:user:persistence:adapter")
include("source:user:persistence:entity")
include("source:user:persistence:repository")
include("source:user:persistence:flyway")
include("source:user:config")


include("source:ui-thymeleaf")