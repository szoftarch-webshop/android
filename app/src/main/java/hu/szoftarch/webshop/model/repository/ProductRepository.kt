package hu.szoftarch.webshop.model.repository

import hu.szoftarch.webshop.model.data.ProductItem

interface ProductRepository {
    suspend fun getProductsBySerialNumber(serialNumbers: Iterable<String>): List<ProductItem>

    suspend fun getProducts(): List<ProductItem>

    suspend fun getProductById(id: Int): ProductItem?

    suspend fun getProductBySerialNumber(serialNumber: String): ProductItem?

    suspend fun addProduct(productItem: ProductItem): Boolean

    suspend fun updateProduct(productItem: ProductItem): Boolean

    suspend fun deleteProduct(id: Int): Boolean
}
