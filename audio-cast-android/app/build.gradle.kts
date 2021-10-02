plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    defaultConfig {
        applicationId = "com.scythe.audiocast"
        compileSdk = ConfigData.compileSdkVersion
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Deps.coreKtx)
    implementation(Deps.appCompat)
    implementation(Deps.material)
    implementation(Deps.composeUi)
    implementation(Deps.composeMaterial)
    implementation(Deps.composePreview)
    implementation(Deps.lifecycleKtx)
    implementation(Deps.activityCompose)
    implementation(Deps.constraintLayoutCompose)
    implementation(Deps.composeLiveData)
    implementation(Deps.viewModelCompose)
    implementation(Deps.hilt)
    implementation(Deps.retrofit)
    implementation(Deps.retrofitGson)
    implementation(Deps.okHttpLoggingInterceptor)

    kapt(Deps.hiltAnnotations)

    testImplementation(Deps.junit)

    androidTestImplementation(Deps.junitExt)
    androidTestImplementation(Deps.espresso)
    androidTestImplementation(Deps.composeJunit)
    debugImplementation(Deps.composeTooling)
}