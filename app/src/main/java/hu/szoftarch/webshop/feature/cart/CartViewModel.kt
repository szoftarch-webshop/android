package hu.szoftarch.webshop.feature.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.szoftarch.webshop.model.data.ProductItem
import hu.szoftarch.webshop.model.repository.CartRepository
import hu.szoftarch.webshop.model.repository.ProductRepository
import hu.szoftarch.webshop.model.service.PaymentService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val paymentService: PaymentService,
    private val productRepository: ProductRepository
) : ViewModel() {
    var productItems by mutableStateOf<Map<ProductItem, Int>>(mapOf())
        private set

    fun load() = viewModelScope.launch {
        productItems = cartRepository.getProductsInCart().products
            .map { (id, count) -> productRepository.getProductById(id) to count }.toMap()
    }

    fun onAdd(productId: Int) = viewModelScope.launch {
        productItems = cartRepository.addToCart(productId).products
            .map { (id, count) -> productRepository.getProductById(id) to count }.toMap()
    }

    fun onRemove(productId: Int) = viewModelScope.launch {
        productItems = cartRepository.removeFromCart(productId).products
            .map { (id, count) -> productRepository.getProductById(id) to count }.toMap()
    }
}
