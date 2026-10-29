// Archivo de build de nivel superior (plugins comunes a todos los módulos).
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
