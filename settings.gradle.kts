rootProject.name = "doma-compile-plugin"

val releaseVersion = settings.startParameter.projectProperties["release.releaseVersion"]

if (releaseVersion != null) {
    include("compile")
} else {
    pluginManagement {
        includeBuild("compile")
    }
    include("compile-java-test")
    include("compile-kotlin-test")
    include("compile-mix-test")
}
