package com.example.menu_v2

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.menu_v2.screens.MenuScreen
import com.example.menu_v2.screens.StartScreen
import com.example.menu_v2.screens.CartScreen
import com.example.menu_v2.screens.RecommendationScreen
import com.example.menu_v2.screens.PositionScreen
import com.example.menu_v2.R  // ✅ Добавляем импорт ресурсов

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = "startScreen") {
        composable("startScreen") {
            StartScreen {
                navController.navigate("menuScreen")
            }
        }
        composable("menuScreen") {
            MenuScreen(navController)
        }
        composable("cartScreen") {
            CartScreen(navController)
        }
        composable("recommendationScreen") {
            RecommendationScreen(navController)
        }
        composable("positionScreen/{imageRes}/{name}/{price}/{description}") { backStackEntry ->
            val imageRes = backStackEntry.arguments?.getString("imageRes")?.toIntOrNull() ?: R.drawable.ic_launcher_foreground
            val name = backStackEntry.arguments?.getString("name") ?: "Неизвестно"
            val price = backStackEntry.arguments?.getString("price") ?: "0 ₽"
            val description = backStackEntry.arguments?.getString("description") ?: "Описание отсутствует"

            PositionScreen(navController, imageRes, name, price, description)
        }
    }
}
