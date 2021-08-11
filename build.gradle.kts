import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("org.springframework.boot") version "2.3.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
    id("com.google.cloud.tools.jib") version "2.6.0"
}

group = "com.ort"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

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
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity4:3.0.2.RELEASE")
    implementation("org.thymeleaf.extras:thymeleaf-extras-java8time:3.0.0.RELEASE")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.thymeleaf:thymeleaf:3.0.9.RELEASE")
    implementation("org.thymeleaf:thymeleaf-spring4:3.0.9.RELEASE")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:2.3.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") {
        exclude("com.zaxxer:HikariCP")
    }
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.dbflute:dbflute-runtime:1.2.1")
    val mysqlConnectorVersion = if (System.getenv("MYSQL_CONNECTOR_VERSION") != null) {
        System.getenv("MYSQL_CONNECTOR_VERSION")
    } else "8.0.25"
    implementation("mysql:mysql-connector-java:$mysqlConnectorVersion")
    testImplementation("com.codeborne:selenide:5.5.0")
    implementation("org.apache.commons:commons-lang3:3.6")
    implementation("org.apache.commons:commons-collections4:4.1")
    implementation("org.twitter4j:twitter4j-core:4.0.7")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

jib {
    from {
        image = "arm64v8/openjdk:8"
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
            "-server",
            "-Djava.awt.headless=true",
            "-Dspring.profiles.active=production"
        )
        creationTime = "USE_CURRENT_TIMESTAMP"
    }
}
