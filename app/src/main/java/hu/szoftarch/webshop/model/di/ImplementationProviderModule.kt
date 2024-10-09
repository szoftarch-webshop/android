package hu.szoftarch.webshop.model.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.szoftarch.webshop.model.api.ApiService
import hu.szoftarch.webshop.model.datasource.api.ValidationServiceImpl
import hu.szoftarch.webshop.model.datasource.impl.CartRepositoryImpl
import hu.szoftarch.webshop.model.datasource.impl.CategoryRepositoryImpl
import hu.szoftarch.webshop.model.datasource.impl.ProductRepositoryImpl
import hu.szoftarch.webshop.model.datasource.mock.PaymentServiceMock
import hu.szoftarch.webshop.model.repository.CartRepository
import hu.szoftarch.webshop.model.repository.CategoryRepository
import hu.szoftarch.webshop.model.repository.ProductRepository
import hu.szoftarch.webshop.model.service.PaymentService
import hu.szoftarch.webshop.model.service.ValidationService

@Module
@InstallIn(SingletonComponent::class)
object ImplementationProviderModule {
    @Provides
    fun cartRepository(): CartRepository = CartRepositoryImpl

    @Provides
    fun provideProductRepository(apiService: ApiService): ProductRepository  {
        return ProductRepositoryImpl(apiService)
    }

    @Provides
    fun categoryRepository(apiService: ApiService): CategoryRepository {
        return CategoryRepositoryImpl(apiService)
    }

    @Provides
    fun paymentService(): PaymentService = PaymentServiceMock

    @Provides
    fun validationService(): ValidationService = ValidationServiceImpl
}
