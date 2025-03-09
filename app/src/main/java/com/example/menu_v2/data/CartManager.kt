package com.example.menu_v2.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

object CartManager {
    data class CartItem(val imageRes: Int, val name: String, val price: String, val quantity: Int = 1)

    val cartItems = mutableStateListOf<CartItem>()
    val totalPrice = mutableStateOf(0)

    fun addToCart(imageRes: Int, name: String, price: String) {
        val existingItem = cartItems.find { it.name == name }
        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            updateCartItem(existingItem, updatedItem)
        } else {
            cartItems.add(CartItem(imageRes, name, price))
        }
        updateTotalPrice()
    }

    fun removeFromCart(cartItem: CartItem) {
        cartItems.remove(cartItem)
        updateTotalPrice()
    }

    fun decreaseQuantity(cartItem: CartItem) {
        val existingItem = cartItems.find { it.name == cartItem.name }
        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                val updatedItem = existingItem.copy(quantity = existingItem.quantity - 1)
                updateCartItem(existingItem, updatedItem)
            } else {
                cartItems.remove(existingItem)
            }
            updateTotalPrice()
        }
    }

    private fun updateCartItem(oldItem: CartItem, newItem: CartItem) {
        val index = cartItems.indexOf(oldItem)
        if (index != -1) {
            cartItems[index] = newItem
        }
    }

    private fun updateTotalPrice() {
        totalPrice.value = cartItems.sumOf { it.price.replace(" ₽", "").toInt() * it.quantity }
    }
}
