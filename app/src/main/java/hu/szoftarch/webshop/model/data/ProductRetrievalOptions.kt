package hu.szoftarch.webshop.model.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductRetrievalOptions(
    val pageNumber: Int = 1,
    val pageSize: Int = 10,
    val sortBy: String = "name",
    val sortDirection: String = "asc",
    val minPrice: Int? = null,
    val maxPrice: Int? = null,
    val category: String? = null,
    val material: String? = null,
    val searchString: String? = null
)
