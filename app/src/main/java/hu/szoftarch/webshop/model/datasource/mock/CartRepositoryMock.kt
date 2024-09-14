package hu.szoftarch.webshop.model.datasource.mock

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.szoftarch.webshop.model.repository.CartRepository

object CartRepositoryMock : CartRepository {
    private val cartItems = mutableListOf<String>()

    override suspend fun addToCart(productId: String) {
        cartItems.add(productId)
    }

    override suspend fun removeFromCart(productId: String) {
        cartItems.remove(productId)
    }

    override suspend fun productCount(productId: String) = cartItems.count { it == productId }

    override suspend fun cartItems() = cartItems
}

@Module
@InstallIn(SingletonComponent::class)
object CartRepositoryModule {
    @Provides
    fun provideCartRepository(): CartRepository = CartRepositoryMock
}
