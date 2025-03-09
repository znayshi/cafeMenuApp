package com.example.menu_v2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.menu_v2.R
import com.example.menu_v2.data.CartManager
import com.example.menu_v2.ui.theme.MenuV2Theme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items


@Composable
fun MenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F0ED))
            .padding(horizontal = 16.dp)
    ) {
        CategoryRow()

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            MenuGrid(navController)
        }

        Spacer(modifier = Modifier.height(8.dp))

        BottomNavigationBar(navController, "menuScreen")
    }
}

@Composable
fun CategoryRow() {
    val categories = listOf("Закуски", "Супы", "Горячие блюда", "Гарниры", "Десерты", "Напитки", "Специальные предложения")

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            Button(
                onClick = { /* TODO: Фильтрация */ },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB6C4A2)),
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text(text = category, fontSize = 14.sp, color = Color.Black)
            }
        }
    }
}



@Composable
fun MenuGrid(navController: NavController) {
    val dishes = listOf(
        Triple(R.drawable.rolls, "Роллы", "799 ₽"),
        Triple(R.drawable.sushi, "Суши", "900 ₽"),
        Triple(R.drawable.wok, "Вок", "850 ₽"),
        Triple(R.drawable.cheesecake, "Чизкейк", "400 ₽"),
        Triple(R.drawable.tiramisu, "Тирамису", "500 ₽")
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(
            top = 8.dp,
            bottom = 80.dp
        )
    ) {
        items(dishes) { (image, name, price) ->
            MenuItemCard(
                navController = navController,
                imageRes = image,
                dishName = name,
                price = price,
                description = "Описание блюда $name"
            )
        }
    }
}




@Composable
fun MenuItemCard(
    navController: NavController,
    imageRes: Int,
    dishName: String,
    price: String,
    description: String
) {
    var isAddedToCart by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(8.dp)
            .clickable {
                navController.navigate("positionScreen/$imageRes/$dishName/$price/$description")
            }
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = dishName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Text(text = dishName, fontSize = 14.sp, color = Color.Black)
            Text(text = price, fontSize = 16.sp, color = Color.Black)


            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {
                IconButton(
                    onClick = {
                        isAddedToCart = !isAddedToCart
                        if (isAddedToCart) {
                            CartManager.addToCart(imageRes, dishName, price)
                        } else {
                            CartManager.removeFromCart(CartManager.CartItem(imageRes, dishName, price))
                        }
                    },
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.White, shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "Добавить в корзину",
                        tint = if (isAddedToCart) Color.Red else Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFB6C4A2), shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf(
                Triple("Рекомендации", "recommendationScreen", Icons.Outlined.FavoriteBorder),
                Triple("Меню", "menuScreen", Icons.Outlined.AccountBox),
                Triple("Корзина", "cartScreen", Icons.Outlined.ShoppingCart)
            ).forEach { (label, route, icon) ->
                val isSelected = currentRoute == route

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            if (!isSelected) {
                                navController.navigate(route)
                            }
                        }
                        .padding(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(24.dp),
                        tint = if (isSelected) Color.Black else Color.DarkGray
                    )
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        color = if (isSelected) Color.Black else Color.DarkGray
                    )
                }
            }
        }
    }
}





@Composable
fun PreviewMenuScreen() {
    MenuV2Theme {
        MenuScreen(navController = rememberNavController())
    }
}
