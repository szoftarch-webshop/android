import hu.szoftarch.webshop.model.data.ProductItem

data class FilterOptions(
    val nameOrSerialNumber: String = "",
    val categoryId: Int = -1
) {
    fun matches(product: ProductItem): Boolean {
        val nameOrSerialNumber = nameOrSerialNumber.trim().lowercase()
        val nameOrSerialNumberOk =
            nameOrSerialNumber.isBlank() || nameOrSerialNumber in product.name.trim()
                .lowercase() || nameOrSerialNumber in product.serialNumber.trim().lowercase()
        val categoryIdOk = categoryId == -1 || categoryId in product.categoryIds
        return nameOrSerialNumberOk && categoryIdOk
    }
}
