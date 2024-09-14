package hu.szoftarch.webshop.model.datasource.impl

import hu.szoftarch.webshop.model.repository.CartRepository

object CartRepositoryImpl : CartRepository {
    private val cartItems = mutableMapOf<String, Int>()

    override suspend fun addToCart(productId: String) {
        cartItems[productId] = cartItems.getOrDefault(productId, defaultValue = 0) + 1
    }

    override suspend fun removeFromCart(productId: String) {
        if (cartItems.getOrDefault(productId, defaultValue = 0) == 0) {
            return
        }
        cartItems[productId] = cartItems.getOrDefault(productId, defaultValue = 0) - 1
    }

    override suspend fun productCount(productId: String) = cartItems.getOrPut(productId) { 0 }

    override suspend fun cartItems() = cartItems.filter { it.value > 0 }.keys
}
