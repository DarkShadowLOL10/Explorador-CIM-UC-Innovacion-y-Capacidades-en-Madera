package com.example.innovamadera_grupo2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaClaro = lightColorScheme(
    primary = VerdeBosque,
    onPrimary = Color.White,
    primaryContainer = VerdeContenedor,
    onPrimaryContainer = VerdeBosqueOscuro,
    secondary = Color(0xFF4F6354),
    tertiary = Lima,
    onTertiary = LimaOscuro,
    tertiaryContainer = LimaContenedor,
    onTertiaryContainer = LimaOscuro,
    background = FondoClaro,
    onBackground = Color(0xFF1A1C1A),
    surface = Color.White,
    onSurface = Color(0xFF1A1C1A)
)

private val EsquemaOscuro = darkColorScheme(
    primary = VerdeClaro,
    onPrimary = VerdeBosqueOscuro,
    primaryContainer = VerdeBosque,
    onPrimaryContainer = VerdeContenedor,
    secondary = Color(0xFFB4CCB9),
    tertiary = Lima,
    onTertiary = LimaOscuro,
    tertiaryContainer = Color(0xFF3E4C00),
    onTertiaryContainer = LimaContenedor,
    background = FondoOscuro,
    onBackground = Color(0xFFE1E3DE),
    surface = SuperficieOscura,
    onSurface = Color(0xFFE1E3DE)
)

@Composable
fun InnovaMadera_Grupo2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) EsquemaOscuro else EsquemaClaro,
        typography = Typography,
        content = content
    )
}
