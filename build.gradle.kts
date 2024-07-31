import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("java")
    id("com.diffplug.spotless") version "6.10.0"
}

group = "com.kodax"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
    implementation("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
        exceptionFormat = TestExceptionFormat.FULL
        showCauses = true
        showExceptions = true
        showStackTraces = true
    }
}

spotless {
    java {
        googleJavaFormat() // 구글 스타일 가이드 사용
        importOrder("java", "javax", "org", "com", "korea-digital-asset-corp-public")
        removeUnusedImports()
    }
    format("misc") {
        target("**/*.gradle", "**/*.md", "**/*.yml", "**/*.yaml")
        indentWithSpaces()
        endWithNewline()
    }
}