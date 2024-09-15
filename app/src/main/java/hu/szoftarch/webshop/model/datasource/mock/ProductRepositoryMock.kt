package hu.szoftarch.webshop.model.datasource.mock

import hu.szoftarch.webshop.model.data.ProductItem
import hu.szoftarch.webshop.model.repository.FilterOptions
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
        ), ProductItem(
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
        )
    )

    override suspend fun getProducts() = productItems.toList()

    override suspend fun getProducts(filterOptions: FilterOptions) =
        productItems.filter(filterOptions::matches)

    override suspend fun getProductsBySerialNumber(serialNumbers: Set<String>) =
        productItems.filter { it.serialNumber in serialNumbers }
}
