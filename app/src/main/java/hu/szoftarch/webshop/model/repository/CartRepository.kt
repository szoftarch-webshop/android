package hu.szoftarch.webshop.model.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.szoftarch.webshop.model.datasource.impl.CartRepositoryImpl

interface CartRepository {
    suspend fun addToCart(productId: String)
    suspend fun removeFromCart(productId: String)
    suspend fun productCount(productId: String): Int
    suspend fun cartItems(): Set<String>
}

@Module
@InstallIn(SingletonComponent::class)
object CartRepositoryModule {
    @Provides
    fun provideCartRepository(): CartRepository = CartRepositoryImpl
}
