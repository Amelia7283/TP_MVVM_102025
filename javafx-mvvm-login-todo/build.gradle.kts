plugins {
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories { mavenCentral() }

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("com.mysql:mysql-connector-j:8.3.0")
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

javafx {
    version = "21.0.3"
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("com.example.exercice.App")
}

tasks.test {
    useJUnitPlatform()
}
