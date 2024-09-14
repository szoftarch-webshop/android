package hu.szoftarch.webshop.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Products(
    @Json(name = "totalItems")
    val totalItems: Int,
    @Json(name = "items")
    val products: List<Product>,
    @Json(name = "currentPage")
    val currentPage: Int,
    @Json(name = "totalPages")
    val totalPages: Int,
)
