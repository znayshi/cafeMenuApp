package com.example.menu_v2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.example.menu_v2.R
import com.example.menu_v2.data.CartManager

@Composable
fun RecommendationScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F0ED))
            .padding(16.dp)
    ) {

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recommendedDishes) { dish ->
                    val quantity = CartManager.cartItems.find { it.name == dish.name }?.quantity ?: 0

                    RecommendationItemCard(
                        navController = navController,
                        dish = dish,
                        onAddToCart = { imageRes, name, price ->
                            CartManager.addToCart(imageRes, name, price)
                        },
                        onRemoveFromCart = { imageRes, name ->
                            CartManager.removeFromCart(CartManager.CartItem(imageRes, name, "", quantity))
                        },
                        onDecreaseQuantity = { imageRes, name ->
                            CartManager.decreaseQuantity(CartManager.CartItem(imageRes, name, "", quantity))
                        },
                        quantity = quantity
                    )
                }
            }
        }


        BottomNavigationBar(navController, "recommendationScreen")
    }
}


data class Dish(
    val imageRes: Int,
    val name: String,
    val price: String,
    val description: String
)


val recommendedDishes = listOf(
    Dish(R.drawable.rolls, "Роллы", "799 ₽", "Вкусные роллы с лососем."),
    Dish(R.drawable.wok, "Вок", "800 ₽", "Жареная лапша с курицей и овощами."),
    Dish(R.drawable.cheesecake, "Чизкейк", "300 ₽", "Классический чизкейк с клубникой.")
)


@Composable
fun RecommendationItemCard(
    navController: NavController,
    dish: Dish,
    onAddToCart: (Int, String, String) -> Unit,
    onRemoveFromCart: (Int, String) -> Unit,
    onDecreaseQuantity: (Int, String) -> Unit,
    quantity: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("positionScreen/${dish.imageRes}/${dish.name}/${dish.price}/${dish.description}")
            },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = dish.imageRes),
                contentDescription = dish.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = dish.name, fontSize = 16.sp, color = Color.Black)
                Text(text = dish.price, fontSize = 14.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.width(8.dp))


            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { onDecreaseQuantity(dish.imageRes, dish.name) },
                    modifier = Modifier.size(36.dp),
                    enabled = quantity > 0
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = "Уменьшить",
                        tint = if (quantity > 0) Color.Black else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = quantity.toString(),
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )

                IconButton(
                    onClick = { onAddToCart(dish.imageRes, dish.name, dish.price) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "Добавить",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { onRemoveFromCart(dish.imageRes, dish.name) },
                modifier = Modifier.size(36.dp),
                enabled = quantity > 0
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Удалить",
                    tint = if (quantity > 0) Color.Red else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
