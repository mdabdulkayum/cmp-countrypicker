plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.kotlinMultiplatform) apply  false
    alias(libs.plugins.vanniktech.mavenPublish) apply false
    id("org.jetbrains.kotlin.plugin.compose") version libs.versions.kotlin.get() apply false

}
