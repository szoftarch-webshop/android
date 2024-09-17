package hu.szoftarch.webshop.model.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaginatedProducts(
    val totalItems: Int,
    val products: List<ProductItem>,
    val currentPage: Int,
    val totalPages: Int
)
