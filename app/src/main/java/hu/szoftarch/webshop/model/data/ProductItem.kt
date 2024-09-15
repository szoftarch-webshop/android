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
) : Comparable<ProductItem> {
    val priceHuf = "$price HUF"

    override fun compareTo(other: ProductItem) = name.compareTo(other.name)

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other !is ProductItem) {
            return false
        }

        return serialNumber == other.serialNumber || id == other.id
    }

    override fun hashCode() = 31 * serialNumber.hashCode() + id.hashCode()
}
