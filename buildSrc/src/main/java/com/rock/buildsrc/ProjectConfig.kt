package com.rock.buildsrc

object ProjectConfig {
    const val compileSdkVersion = 33
    const val minSdkVersion = 26
    const val targetSdkVersion = 33
    const val applicationId = "com.rock.wanandroid"
    const val kotlinCompilerExtensionVersion = Version.compose
    const val versionCode = 1
    const val versionName = "1.0"
    const val jvmTarget = "1.8"
    const val compose = true //启用 compose
    const val minifyEnabled = true // 启用 R8 编译器
}