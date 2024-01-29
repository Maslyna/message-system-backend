val eurekaServerVersion = "4.1.0"

plugins {
    id("java")
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "net.maslyna"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server:${eurekaServerVersion}")
}

tasks.test {
    useJUnitPlatform()
}