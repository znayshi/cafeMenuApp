package com.example.menu_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.menu_v2.Navigation
import com.example.menu_v2.ui.theme.MenuV2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenuV2Theme {
                val navController = rememberNavController()
                Navigation(navController)
            }
        }
    }
}
