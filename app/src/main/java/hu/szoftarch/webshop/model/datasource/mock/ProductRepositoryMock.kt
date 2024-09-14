package hu.szoftarch.webshop.model.datasource.mock

import hu.szoftarch.webshop.model.data.Product
import hu.szoftarch.webshop.model.repository.ProductRepository
import javax.inject.Inject


class ProductRepositoryMock @Inject constructor(): ProductRepository  {
    private val products = mutableListOf(
        Product(
            id = "1",
            serialNumber = "P001",
            name = "Laptop",
            weight = 2.5,
            material = "Aluminium",
            description = "High-performance laptop",
            price = 1200.0,
            stock = 50,
            categoryNames = listOf("1"),
            imageUrl = "http://example.com/laptop.png"
        ),
        Product(
            id = "2",
            serialNumber = "P002",
            name = "Smartphone",
            weight = 0.2,
            material = "Glass",
            description = "Latest model smartphone",
            price = 800.0,
            stock = 100,
            categoryNames = listOf("2"),
            imageUrl = "http://example.com/smartphone.png"
        )
    )

    override suspend fun getProducts(): List<Product> {
        return products
    }

    override suspend fun getProductById(productId: String): Product? {
        return products.find { it.id == productId }
    }

    override suspend fun addProduct(product: Product): Boolean {
        return products.add(product)
    }

    override suspend fun updateProduct(product: Product): Boolean {
        val index = products.indexOfFirst { it.id == product.id }
        if (index != -1) {
            products[index] = product
            return true
        }
        return false
    }

    override suspend fun deleteProduct(productId: String): Boolean {
        return products.removeIf { it.id == productId }
    }
}