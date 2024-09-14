package hu.szoftarch.webshop.model.repository

import hu.szoftarch.webshop.model.data.ProductItem

interface CartRepository {
    suspend fun addToCart(product: ProductItem): Map<ProductItem, Int>

    suspend fun removeFromCart(product: ProductItem): Map<ProductItem, Int>

    suspend fun getProductsInCart(): Map<ProductItem, Int>

    suspend fun getProductCount(products: Iterable<ProductItem>): Map<ProductItem, Int>
}
