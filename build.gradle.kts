import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("org.springframework.boot") version "2.3.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.spring") version "1.4.32"
    id("com.google.cloud.tools.jib") version "2.6.0"
}

group = "com.ort"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
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
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity5")
    implementation("org.thymeleaf.extras:thymeleaf-extras-java8time:3.0.0.RELEASE")
    implementation("org.thymeleaf:thymeleaf:3.0.9.RELEASE")
    implementation("org.thymeleaf:thymeleaf-spring4:3.0.9.RELEASE")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:2.3.0")
    // dbflute, myself
    implementation("org.dbflute:dbflute-runtime:1.2.1")
    val mysqlConnectorVersion = if (System.getenv("MYSQL_CONNECTOR_VERSION") != null) {
        System.getenv("MYSQL_CONNECTOR_VERSION")
    } else "8.0.25"
    implementation("mysql:mysql-connector-java:$mysqlConnectorVersion")
    // apache commons
    implementation("org.apache.commons:commons-lang3:3.6")
    implementation("org.apache.commons:commons-collections4:4.1")
    // twitter
    implementation("org.twitter4j:twitter4j-core:4.0.7")
    // jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // test
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("com.codeborne:selenide:5.5.0")
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
            "-Dspring.profiles.active=playground"
        )
        creationTime = "USE_CURRENT_TIMESTAMP"
    }
}
