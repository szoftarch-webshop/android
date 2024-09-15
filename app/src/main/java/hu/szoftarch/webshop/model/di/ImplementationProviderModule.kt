package hu.szoftarch.webshop.model.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.szoftarch.webshop.model.datasource.mock.CartRepositoryMock
import hu.szoftarch.webshop.model.datasource.mock.CategoryRepositoryMock
import hu.szoftarch.webshop.model.datasource.mock.ProductRepositoryMock
import hu.szoftarch.webshop.model.repository.CartRepository
import hu.szoftarch.webshop.model.repository.CategoryRepository
import hu.szoftarch.webshop.model.repository.ProductRepository

@Module
@InstallIn(SingletonComponent::class)
object ImplementationProviderModule {
    @Provides
    fun cartRepository(): CartRepository = CartRepositoryMock

    @Provides
    fun productRepository(): ProductRepository = ProductRepositoryMock

    @Provides
    fun categoryRepository(): CategoryRepository = CategoryRepositoryMock
}
