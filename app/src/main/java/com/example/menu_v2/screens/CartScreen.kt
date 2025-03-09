package com.example.menu_v2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.navigation.NavController
import com.example.menu_v2.R
import com.example.menu_v2.data.CartManager
import androidx.compose.ui.draw.clip

@Composable
fun CartScreen(navController: NavController) {
    val cartItems by remember { derivedStateOf { CartManager.cartItems.toList() } }
    val totalPrice by remember { derivedStateOf { CartManager.totalPrice.value } }

    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F0ED))
            .padding(16.dp)
    ) {


        if (cartItems.isEmpty()) {
            Text("Ваша корзина пуста", fontSize = 18.sp, color = Color.Gray)
        } else {
            LazyColumn {
                items(cartItems) { cartItem ->
                    CartItemRow(cartItem)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Black, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Сумма: ${totalPrice} ₽",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }

                Button(
                    onClick = {
                        if (Math.random() > 0.5) showSuccess = true else showError = true
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Заказать", fontSize = 16.sp, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomNavigationBar(navController, "cartScreen")
    }

    if (showSuccess) {
        NotificationDialog(
            text = "Заказ успешно оформлен,\nдождитесь официанта для подтверждения!",
            onDismiss = { showSuccess = false }
        )
    }
    if (showError) {
        NotificationDialog(
            text = "Что-то пошло не так,\nпопробуйте позже или обратитесь к официанту(",
            onDismiss = { showError = false }
        )
    }
}

@Composable
fun CartItemRow(cartItem: CartManager.CartItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
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
                painter = painterResource(id = cartItem.imageRes),
                contentDescription = cartItem.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = cartItem.name, fontSize = 16.sp, color = Color.Black)
                Text(text = "${cartItem.price} ₽", fontSize = 14.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { CartManager.decreaseQuantity(cartItem) },
                    modifier = Modifier.size(36.dp),
                    enabled = cartItem.quantity > 0
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = "Уменьшить",
                        tint = if (cartItem.quantity > 0) Color.Black else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = cartItem.quantity.toString(),
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )

                IconButton(
                    onClick = { CartManager.addToCart(cartItem.imageRes, cartItem.name, cartItem.price) },
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
    }
}



@Composable
fun NotificationDialog(text: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { Button(onClick = onDismiss) { Text("Закрыть") } },
        text = { Text(text, fontSize = 16.sp, color = Color.Black) }
    )
}
