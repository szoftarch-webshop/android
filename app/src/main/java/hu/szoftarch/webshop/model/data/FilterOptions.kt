package hu.szoftarch.webshop.model.data

data class FilterOptions(
    val nameOrSerialNumber: String = "",
    val material: String = "",
    val categoryId: Int = -1
) {
    fun matches(product: ProductItem): Boolean {
        val nameOrSerialNumber = nameOrSerialNumber.trim().lowercase()
        val nameOrSerialNumberOk = nameOrSerialNumber.isBlank()
                || product.name.trim().lowercase().startsWith(nameOrSerialNumber)
                || product.serialNumber.trim().lowercase().startsWith(nameOrSerialNumber)

        val material = material.trim().lowercase()
        val materialOk = material.isBlank()
                || product.material.trim().lowercase().startsWith(material)

        val categoryIdOk = categoryId == -1 || categoryId in product.categoryIds

        return nameOrSerialNumberOk && materialOk && categoryIdOk
    }
}
