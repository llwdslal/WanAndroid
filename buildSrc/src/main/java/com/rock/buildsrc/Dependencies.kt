package com.rock.buildsrc

object Version {
    const val compose = "1.2.0-beta02"
}

object Libs {
    object AndroidX {

        object Activity {
            const val compose = "androidx.activity:activity-compose:1.3.1"
        }

        object Appcompat{
            const val appcompat = "androidx.appcompat:appcompat:1.3.0"
        }

        object Core {
            private const val version = "1.7.0"
            const val ktx = "androidx.core:core-ktx:$version"
        }

        object Compose {
            private const val version = Version.compose
            const val ui = "androidx.compose.ui:ui:$version"
            const val material = "androidx.compose.material3:material3:1.0.0-alpha12"
            const val uiPreview = "androidx.compose.ui:ui-tooling-preview:$version"
            const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
        }

        object Lifecycle {
            private const val version = "2.4.1"
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        }

        object Test {
            const val extJunit = "androidx.test.ext:junit:1.1.3"
            const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        }
    }

    object Junit {
        const val junit = "junit:junit:4.13.2"
    }
}