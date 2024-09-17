package hu.szoftarch.webshop.model.repository

import hu.szoftarch.webshop.model.data.CartContent

interface CartRepository {
    suspend fun addToCart(productId: Int): CartContent

    suspend fun removeFromCart(productId: Int): CartContent

    suspend fun getProductsInCart(): CartContent

    suspend fun getProductCount(productIds: List<Int>): Map<Int, Int>
}
