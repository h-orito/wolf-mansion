plugins {
    java
    id("org.springframework.boot") version "3.5.9"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("com.google.cloud.tools.jib") version "3.4.4"
}

group = "com.ort"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

apply(plugin = "kotlin")
apply(plugin = "io.spring.dependency-management")

repositories {
    mavenCentral()
}

sourceSets {
    getByName("test").java.srcDirs("src/test")
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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // thymeleaf
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.3.0")
    // dbflute, mysql
    implementation("org.dbflute:dbflute-runtime:1.3.1")
    implementation("com.mysql:mysql-connector-j")
    // apache commons
    implementation("org.apache.commons:commons-lang3")
    implementation("org.apache.commons:commons-collections4:4.4")
    // jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // test
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "21"
    }
}

jib {
    from {
        image = "eclipse-temurin:21-jre-jammy"
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
