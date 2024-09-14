package hu.szoftarch.webshop.model.repository

import hu.szoftarch.webshop.model.data.ProductItem

interface ProductRepository {
    suspend fun getProducts(): List<ProductItem>

    suspend fun getProductsBySerialNumber(serialNumbers: Set<String>): List<ProductItem>
}
