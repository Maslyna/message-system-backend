val springBootVersion = "3.2.1"
val eurekaClientVersion = "4.1.0"

plugins {
    id("java")
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("io.freefair.lombok") version "8.4"
}
group = "net.maslyna.message"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.data:spring-data-cassandra")

    implementation("org.cognitor.cassandra:cassandra-migration:2.6.1_v4")
    implementation("org.cognitor.cassandra:cassandra-migration-spring-boot-starter:2.6.1_v4")

    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:${eurekaClientVersion}")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    implementation("org.springframework.data:spring-data-cassandra")

}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:${springBootVersion}")
    }
}

tasks.test {
    useJUnitPlatform()
}
