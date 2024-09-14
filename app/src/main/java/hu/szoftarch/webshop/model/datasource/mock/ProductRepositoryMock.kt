package hu.szoftarch.webshop.model.datasource.mock

import hu.szoftarch.webshop.model.data.ProductItem
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
            categoryNames = listOf("1"),
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
            categoryNames = listOf("2"),
            imageUrl = "https://picsum.photos/700/400"
        )
    )

    override suspend fun getProductsBySerialNumber(serialNumbers: Iterable<String>) =
        productItems.filter { it.serialNumber in serialNumbers }

    override suspend fun getProducts(): List<ProductItem> {
        return productItems
    }

    override suspend fun getProductById(id: Int): ProductItem? {
        return productItems.find { it.id == id }
    }

    override suspend fun getProductBySerialNumber(serialNumber: String) = productItems.first()

    override suspend fun addProduct(productItem: ProductItem): Boolean {
        return productItems.add(productItem)
    }

    override suspend fun updateProduct(productItem: ProductItem): Boolean {
        val index = productItems.indexOfFirst { it.id == productItem.id }
        if (index != -1) {
            productItems[index] = productItem
            return true
        }
        return false
    }

    override suspend fun deleteProduct(id: Int): Boolean {
        return productItems.removeIf { it.id == id }
    }
}
