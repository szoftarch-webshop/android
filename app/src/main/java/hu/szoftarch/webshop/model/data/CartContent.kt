package hu.szoftarch.webshop.model.data

import kotlin.math.max

data class CartContent(val products: Map<Int, Int>) {
    fun add(id: Int): CartContent {
        val products = products.toMutableMap()
        products[id] = (products[id] ?: 0) + 1
        return CartContent(products = products)
    }

    fun remove(id: Int): CartContent {
        val products = products.toMutableMap()
        products[id] = max((products[id] ?: 0) - 1, 0)
        if (products[id] == 0) {
            products.remove(id)
        }
        return CartContent(products = products)
    }
}
