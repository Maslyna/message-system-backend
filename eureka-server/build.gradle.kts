val eurekaServerVersion = "4.1.0"
val loadBalancerVersion = eurekaServerVersion

plugins {
    id("java")
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "net.maslyna"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server:${eurekaServerVersion}")
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer:${loadBalancerVersion}")

}

tasks.test {
    useJUnitPlatform()
}