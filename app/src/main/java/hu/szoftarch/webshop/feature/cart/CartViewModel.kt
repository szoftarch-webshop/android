package hu.szoftarch.webshop.feature.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.szoftarch.webshop.model.data.Product
import hu.szoftarch.webshop.model.repository.CartRepository
import hu.szoftarch.webshop.model.repository.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository, private val productRepository: ProductRepository
) : ViewModel() {
    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    var productCount by mutableStateOf<Map<String, Int>>(emptyMap())
        private set

    init {
        viewModelScope.launch {
            products = cartRepository.cartItems().map {
                productRepository.getProductById(it)!!
            }
            
            productCount = products.associate {
                it.id to cartRepository.productCount(it.id)
            }
        }
    }

    fun addToCart(productId: String) {
        viewModelScope.launch {
            cartRepository.addToCart(productId)
            productCount = productCount.toMutableMap().apply {
                this[productId] = cartRepository.productCount(productId)
            }
        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            cartRepository.removeFromCart(productId)
            productCount = productCount.toMutableMap().apply {
                this[productId] = cartRepository.productCount(productId)
            }
        }
    }
}
