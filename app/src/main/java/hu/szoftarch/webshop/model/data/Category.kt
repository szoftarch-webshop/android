package hu.szoftarch.webshop.model.data

data class Category(
    val uniqueID: String,
    val parentCategoryId: String?,
    val name: String
)