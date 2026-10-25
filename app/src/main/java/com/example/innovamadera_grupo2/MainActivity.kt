package com.example.innovamadera_grupo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.innovamadera_grupo2.ui.AppNavigation
import com.example.innovamadera_grupo2.ui.theme.InnovaMadera_Grupo2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InnovaMadera_Grupo2Theme {
                AppNavigation()
            }
        }
    }
}
