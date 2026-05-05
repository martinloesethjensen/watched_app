import com.android.build.api.variant.BuildConfigField
import java.io.StringReader
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    buildFeatures {
        buildConfig = true
    }

    namespace = "dev.martinloeseth.watchedapp"
    compileSdk = 37

    defaultConfig {
        applicationId = "dev.martinloeseth.watchedapp"
        versionCode = 1
        minSdk = 23
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug { }
        release { }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions.unitTests.isIncludeAndroidResources = true
    buildToolsVersion = "36.1.0"
}

val props = providers.fileContents(
    isolated.rootProject.projectDirectory.file("local.properties")
).asText.map { text ->
    val properties = Properties()
    properties.load(StringReader(text))
    properties
}

androidComponents {
    onVariants { variant ->
        val apiKey = props.map { it.getProperty("API_KEY") ?: "" }
        val apiBaseUrl = props.map { it.getProperty("API_BASE_URL") ?: "" }

        variant.buildConfigFields?.put(
            "API_KEY",
            apiKey.map { BuildConfigField(type = "String", value = "\"$it\"", comment = null) }
        )
        variant.buildConfigFields?.put(
            "API_BASE_URL",
            apiBaseUrl.map { BuildConfigField(type = "String", value = "\"$it\"", comment = null) }
        )
    }
}

dependencies {
    // Compose BOM — manages all androidx.compose versions
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.navigation.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.retrofit.kotlinx.serialization.converter)

    implementation(libs.coil.compose)
    implementation(libs.androidx.datastore.preferences)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}
