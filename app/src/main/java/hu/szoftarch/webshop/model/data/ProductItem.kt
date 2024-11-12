package hu.szoftarch.webshop.model.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductItem(
    val id: Int = 0,
    val serialNumber: String = "0000000000",
    val name: String = "Product",
    val weight: Double = 0.0,
    val material: String = "Unknown",
    val description: String = "No description",
    val price: Int = 0,
    val stock: Int = 0,
    val categoryNames: List<String> = emptyList(),
    val imageUrl: String = "https://picsum.photos/700/400"
) {
    val priceHuf: String
        get() = "$price HUF"
}
