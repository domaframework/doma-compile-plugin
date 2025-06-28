rootProject.name = "doma-compile-plugin"

pluginManagement {
    includeBuild("compile")
}

include("compile-java-test")
include("compile-kotlin-test")
include("compile-mix-test")
