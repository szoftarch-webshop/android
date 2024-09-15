package hu.szoftarch.webshop.model.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryItem(
    val id: Int = 0,
    val name: String = "Category Name"
)
