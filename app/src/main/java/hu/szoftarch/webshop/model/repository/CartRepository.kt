package hu.szoftarch.webshop.model.repository

import hu.szoftarch.webshop.model.data.CartContent

interface CartRepository {
    suspend fun addToCart(productId: Int, quantity: Int): CartContent

    suspend fun removeFromCart(productId: Int,  quantity: Int): CartContent

    suspend fun getProductsInCart(): CartContent

    suspend fun getProductCount(productIds: List<Int>): Map<Int, Int>
}
