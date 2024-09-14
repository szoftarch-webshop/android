package hu.szoftarch.webshop.model.repository

import hu.szoftarch.webshop.model.data.ProductItem

interface ProductRepository {
    suspend fun getProductsBySerialNumber(serialNumbers: Set<String>): List<ProductItem>
}
