package hu.szoftarch.webshop.model.repository

import hu.szoftarch.webshop.model.data.PaginatedProducts
import hu.szoftarch.webshop.model.data.ProductItem
import hu.szoftarch.webshop.model.data.ProductRetrievalOptions

interface ProductRepository {
    suspend fun getProductById(id: Int): ProductItem

    suspend fun getProductBySerialNumber(serialNumber: String): ProductItem

    suspend fun getProducts(options: ProductRetrievalOptions): PaginatedProducts
}
