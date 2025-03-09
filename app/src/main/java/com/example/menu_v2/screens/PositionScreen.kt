package com.example.menu_v2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
fun PositionScreen(navController: NavController, imageRes: Int, name: String, price: String, description: String) {
    val quantity = CartManager.cartItems.find { it.name == name }?.quantity ?: 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F0ED))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Вес:", fontSize = 16.sp, color = Color.Black)
                Text(text = "520 грамм", fontSize = 16.sp, color = Color.Gray)
            }
            Column {
                Text(text = "Калорийность:", fontSize = 16.sp, color = Color.Black)
                Text(text = "700 ккал", fontSize = 16.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = price, fontSize = 20.sp, color = Color.Black)

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { CartManager.decreaseQuantity(CartManager.CartItem(imageRes, name, price, quantity)) },
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
                    onClick = { CartManager.addToCart(imageRes, name, price) },
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
        }

        Spacer(modifier = Modifier.height(8.dp))


        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = description,
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp),
            color = Color.Black
        )

        Spacer(modifier = Modifier.weight(1f))


        Button(
            onClick = { CartManager.addToCart(imageRes, name, price) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(text = "Добавить в корзину", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))


        BottomNavigationBar(navController, "positionScreen")
    }
}


fun NavController.navigateToPosition(imageRes: Int, name: String, price: String, description: String) {
    this.navigate("positionScreen/$imageRes/$name/$price/$description")
}
