package hu.szoftarch.webshop.model.data

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaginatedProducts(
    val totalItems: Int,
    @SerializedName("items")
    val products: List<ProductItem>,
    val currentPage: Int,
    val totalPages: Int
)
