package hu.szoftarch.webshop.model.datasource.mock

import hu.szoftarch.webshop.model.data.PaginatedProducts
import hu.szoftarch.webshop.model.data.ProductItem
import hu.szoftarch.webshop.model.data.ProductRetrievalOptions
import hu.szoftarch.webshop.model.repository.ProductRepository

object ProductRepositoryMock : ProductRepository {
    private val productItems = mutableListOf(
        ProductItem(
            id = 1,
            serialNumber = "1234567890",
            name = "Laptop",
            weight = 2.5,
            material = "Aluminium",
            description = "High-performance laptop",
            price = 1200,
            stock = 50,
            categoryIds = listOf(1),
            imageUrl = "https://picsum.photos/700/500"
        ),
        ProductItem(
            id = 2,
            serialNumber = "0987654321",
            name = "Smartphone",
            weight = 0.2,
            material = "Glass",
            description = "Latest model smartphone",
            price = 800,
            stock = 100,
            categoryIds = listOf(2),
            imageUrl = "https://picsum.photos/700/400"
        ),
        ProductItem(
            id = 5,
            serialNumber = "5432167890",
            name = "Something else",
            weight = 1.0,
            material = "Wood",
            description = "No idea what this is",
            price = 15000,
            stock = 10,
            categoryIds = listOf(1, 2, 3),
            imageUrl = "https://picsum.photos/400/400"
        )
    )

    override suspend fun getProductById(id: Int) =
        productItems.find { it.id == id } ?: throw NoSuchElementException()

    override suspend fun getProductBySerialNumber(serialNumber: String) =
        productItems.find { serialNumber in it.serialNumber } ?: throw NoSuchElementException()

    override suspend fun getProducts(options: ProductRetrievalOptions): PaginatedProducts {
        val products = productItems.filter(options::matches)
        return PaginatedProducts(
            totalItems = products.size,
            products = products,
            currentPage = 1,
            totalPages = 1
        )
    }
}

private fun ProductRetrievalOptions.matches(product: ProductItem): Boolean {
    val nameOrSerialNumber = searchString.trim().lowercase()
    val nameOrSerialNumberOk = nameOrSerialNumber.isBlank()
            || product.name.trim().lowercase().startsWith(nameOrSerialNumber)
            || product.serialNumber.trim().lowercase().startsWith(nameOrSerialNumber)

    val material = material.trim().lowercase()
    val materialOk = material.isBlank()
            || product.material.trim().lowercase().startsWith(material)

    val categoryOk = categoryId == -1 || categoryId in product.categoryIds

    return nameOrSerialNumberOk && materialOk && categoryOk
}
