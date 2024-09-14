package hu.szoftarch.webshop.model.repository

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import hu.szoftarch.webshop.model.data.Product
import hu.szoftarch.webshop.model.datasource.mock.ProductRepositoryMock

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(productId: String): Product?
    suspend fun addProduct(product: Product): Boolean
    suspend fun updateProduct(product: Product): Boolean
    suspend fun deleteProduct(productId: String): Boolean
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductRepositoryModule {

    @Binds
    abstract fun bindProductRepository(impl: ProductRepositoryMock): ProductRepository
}
