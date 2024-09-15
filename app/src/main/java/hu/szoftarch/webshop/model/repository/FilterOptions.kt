import hu.szoftarch.webshop.model.data.ProductItem

data class FilterOptions(
    val nameOrSerialNumber: String = ""
) {
    fun matches(product: ProductItem): Boolean {
        val nameOrSerialNumber = nameOrSerialNumber.trim().lowercase()
        return nameOrSerialNumber.isBlank() || nameOrSerialNumber in product.name.trim()
            .lowercase() || nameOrSerialNumber in product.serialNumber.trim().lowercase()
    }
}
