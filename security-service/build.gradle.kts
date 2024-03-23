val springBootVersion = "3.2.1"
val eurekaClientVersion = "4.1.0"
val jsonWebTokenVersion = "0.12.3"

plugins {
    id("java")
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    id("io.freefair.lombok") version "8.4"
}

group = "net.maslyna.security"
version = "0.1"


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.data:spring-data-r2dbc")
    implementation("org.flywaydb:flyway-core")
    implementation("org.postgresql:r2dbc-postgresql")
    implementation("org.postgresql:postgresql")

    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:${eurekaClientVersion}")

    implementation("io.jsonwebtoken:jjwt-api:${jsonWebTokenVersion}")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:${jsonWebTokenVersion}")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:${jsonWebTokenVersion}")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:r2dbc")
    testImplementation("org.testcontainers:junit-jupiter")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:${springBootVersion}")
    }
}

tasks.test {
    useJUnitPlatform()
}
