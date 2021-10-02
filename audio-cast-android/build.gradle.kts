import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

apply(plugin = "com.github.ben-manes.versions")
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(BuildPlugins.android)
        classpath(BuildPlugins.kotlin)
        classpath(BuildPlugins.hilt)
        classpath(BuildPlugins.gradleDependencies)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}



tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

fun String.isNonStable(): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(this)
    return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {

    rejectVersionIf {
        candidate.version.isNonStable() && !currentVersion.isNonStable()
    }

    checkForGradleUpdate = true
    outputFormatter = "json"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
}