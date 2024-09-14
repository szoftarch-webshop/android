package hu.szoftarch.webshop.model.repository

interface CartRepository {
    suspend fun addToCart(productId: String)
    suspend fun removeFromCart(productId: String)
    suspend fun productCount(productId: String): Int
    suspend fun cartItems(): List<String>
}
