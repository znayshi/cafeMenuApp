package com.example.menu_v2.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.text.style.TextAlign


@Composable
fun StartScreen(onStartClicked: () -> Unit) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var selectedTable by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val tables = listOf("Столик 1", "Столик 2", "Столик 3")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF3F0ED), Color(0xFFA4B494))
                )
            )
            .padding(horizontal = 24.dp, vertical = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Поле ввода имени
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = {
                Text(
                    text = "Введите имя",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp))
                .clickable { expanded = true }
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = selectedTable ?: "Выберите номер столика",
                fontSize = 16.sp,
                color = if (selectedTable != null) Color.Black else Color.Gray
            )
        }


        Box(modifier = Modifier.fillMaxWidth()) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                tables.forEach { table ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                table,
                                color = if (selectedTable == table) Color(0xFF91A78D) else Color.Black
                            )
                        },
                        onClick = {
                            selectedTable = table
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


        Button(
            onClick = {
                if (name.text.isNotBlank() && selectedTable != null) {
                    onStartClicked()
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp)
                .shadow(4.dp, RoundedCornerShape(50)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RoundedCornerShape(50)
        ) {
            Text("Продолжить", fontSize = 16.sp, color = Color.White)
        }
    }
}
