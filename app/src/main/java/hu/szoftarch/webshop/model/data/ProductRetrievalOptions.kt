package hu.szoftarch.webshop.model.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductRetrievalOptions(
    val pageNumber: Int = 1,
    val pageSize: Int = 10,
    val categoryId: Int = -1,
    val material: String = "",
    val searchString: String = ""
)
