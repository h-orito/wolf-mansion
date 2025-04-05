plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.spring") version "2.1.20"
    id("com.google.cloud.tools.jib") version "3.4.4"
}

group = "com.ort"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

apply(plugin = "kotlin")
apply(plugin = "io.spring.dependency-management")

repositories {
    mavenCentral()
}

sourceSets {
    getByName("main").java.srcDirs("src/main/kotlin")
    getByName("main").java.srcDirs("src/main/java")
    getByName("test").java.srcDirs("src/test")
    getByName("main").resources.srcDirs("src/main/resources")
    getByName("test").resources.srcDirs("src/test/resources")
}

dependencies {
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") {
        exclude("com.zaxxer:HikariCP")
    }
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // thymeleaf
    implementation("org.thymeleaf.extras:thymeleaf-extras-java8time:3.0.0.RELEASE")
    implementation("org.thymeleaf:thymeleaf:3.1.3.RELEASE")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.4.0")
    // dbflute, mysql
    implementation("org.dbflute:dbflute-runtime:1.2.8")
    implementation("mysql:mysql-connector-java:8.0.33")
    // jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
    // test
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
tasks.withType<Test> {
    useJUnitPlatform()
}

jib {
    from {
        image = "arm64v8/openjdk:21"
        platforms {
            platform {
                architecture = "arm64"
                os = "linux"
            }
        }
    }
    to {
        image = "ghcr.io/h-orito/wolf-mansion"
    }
    container {
        jvmFlags = listOf(
            "-server", "-Djava.awt.headless=true", "-Dspring.profiles.active=playground"
        )
        creationTime = "USE_CURRENT_TIMESTAMP"
    }
}
