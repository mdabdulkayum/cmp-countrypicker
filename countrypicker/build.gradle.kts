@file:Suppress("DEPRECATION")

import com.android.build.api.dsl.androidLibrary
import com.vanniktech.maven.publish.portal.SonatypeCentralPortal
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.mavenPublish)
    id("org.jetbrains.kotlin.plugin.compose")

}

group = "io.github.mdabdulkayum"
version = "1.0.8"

kotlin {

    androidLibrary {
        namespace = "io.github.mdabdulkayum"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilations.configureEach {
            compilerOptions.configure {
                jvmTarget.set(
                    JvmTarget.JVM_11
                )
            }

        }
    }



    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "countrypicker"
            isStatic = true
        }
    }



    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.runtime)
        }
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)


            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3") // Icon
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(
        "io.github.mdabdulkayum",
        "cmp-countrypicker",
        "1.0.8"
    )

    pom {
        name = "CMP Country Picker"
        description = "Library used to pick country, code and phone number."
        inceptionYear = "2026"
        url = "https://github.com/mdabdulkayum/cmp-countrypicker"
        licenses {
            license {
                name = "MIT"
                url = "https://opensource.org/licenses/MIT"
                distribution = "ZZZ"
            }
        }
        developers {
            developer {
                id = "mdabdulkayum"
                name = "Md Abdul Kayum"
                url = "https://mdabdulkayum.github.io/portfolio/"
            }
        }
        scm {
            url = "https://github.com/mdabdulkayum/cmp-countrypicker"
        }
    }
}
