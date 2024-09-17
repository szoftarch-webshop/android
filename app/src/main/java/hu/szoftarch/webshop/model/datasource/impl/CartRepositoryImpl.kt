package hu.szoftarch.webshop.model.datasource.impl

import hu.szoftarch.webshop.model.data.CartContent
import hu.szoftarch.webshop.model.repository.CartRepository

object CartRepositoryImpl : CartRepository {
    private var cartContent = CartContent(products = mapOf())

    override suspend fun addToCart(productId: Int): CartContent {
        cartContent = cartContent.add(productId)
        return cartContent.copy()
    }

    override suspend fun removeFromCart(productId: Int): CartContent {
        cartContent = cartContent.remove(productId)
        return cartContent.copy()
    }

    override suspend fun getProductsInCart() = cartContent.copy()

    override suspend fun getProductCount(products: List<Int>) =
        products.associateWith { (cartContent.products[it] ?: 0) }
}
