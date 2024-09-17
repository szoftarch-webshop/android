package hu.szoftarch.webshop.model.datasource.mock

import hu.szoftarch.webshop.model.data.ProductItem
import hu.szoftarch.webshop.model.repository.CartRepository
import kotlin.math.max

object CartRepositoryMock : CartRepository {
    private val cartItems = sortedMapOf<ProductItem, Int>()

    override suspend fun addToCart(product: ProductItem): Map<ProductItem, Int> {
        cartItems[product] = (cartItems[product] ?: 0) + 1
        return cartItems.toMap()
    }

    override suspend fun removeFromCart(product: ProductItem): Map<ProductItem, Int> {
        cartItems[product] = max((cartItems[product] ?: 0) - 1, 0)
        return cartItems.toMap()
    }

    override suspend fun getProductsInCart() = cartItems.filter { (_, count) -> count != 0 }

    override suspend fun getProductCount(products: Iterable<ProductItem>) =
        products.associateWith { (cartItems[it] ?: 0) }
}
