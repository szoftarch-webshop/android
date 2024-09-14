package hu.szoftarch.webshop.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "id")
    val id: String,
    @Json(name = "serialNumber")
    val serialNumber: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "weight")
    val weight: Double,
    @Json(name = "material")
    val material: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "price")
    val price: Double,
    @Json(name = "stock")
    val stock: Int,
    @Json(name = "categoryNames")
    val categoryNames: List<String>,
    @Json(name = "imageUrl")
    val imageUrl: String
)