package hu.szoftarch.webshop.model.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.szoftarch.webshop.model.datasource.impl.CartRepositoryImpl
import hu.szoftarch.webshop.model.datasource.mock.ProductRepositoryMock

@Module
@InstallIn(SingletonComponent::class)
object ImplementationProviderModule {
    @Provides
    fun cartRepository(): CartRepository = CartRepositoryImpl

    @Provides
    fun productRepository(): ProductRepository = ProductRepositoryMock
}
