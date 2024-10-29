package hu.szoftarch.webshop.model.datasource.impl

import hu.szoftarch.webshop.model.data.CartContent
import hu.szoftarch.webshop.model.repository.CartRepository

object CartRepositoryImpl : CartRepository {
    private var cartContent = CartContent(products = mapOf())

     override suspend fun addToCart(productId: Int, quantity: Int): CartContent {
        val currentQuantity = cartContent.products[productId] ?: 0
        cartContent = cartContent.copy(
            products = cartContent.products + (productId to (currentQuantity + quantity))
        )
        return cartContent.copy()
    }

    override suspend fun removeFromCart(productId: Int, quantity: Int): CartContent {
        val currentQuantity = cartContent.products[productId] ?: 0
        cartContent = if (currentQuantity > quantity) {
            cartContent.copy(
                products = cartContent.products.toMutableMap().apply {
                    this[productId] = currentQuantity - quantity
                }
            )
        } else {
            cartContent.copy(
                products = cartContent.products - productId
            )
        }
        return cartContent.copy()
    }

    override suspend fun getProductsInCart() = cartContent.copy()

    override suspend fun getProductCount(productIds: List<Int>) =
        productIds.associateWith { (cartContent.products[it] ?: 0) }
}
