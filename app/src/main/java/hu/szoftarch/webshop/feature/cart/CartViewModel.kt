package hu.szoftarch.webshop.feature.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.szoftarch.webshop.model.data.CartContent
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

    var total by mutableIntStateOf(0)
        private set

    fun load() = viewModelScope.launch {
        setProductItems(cartRepository.getProductsInCart())
    }

    fun onAdd(productId: Int): Boolean {
        viewModelScope.launch {
            setProductItems(cartRepository.addToCart(productId))
        }
        return true
    }

    fun onRemove(productId: Int): Boolean {
        viewModelScope.launch {
            setProductItems(cartRepository.removeFromCart(productId))
        }
        return true
    }

    private suspend fun setProductItems(cartContent: CartContent) {
        productItems =
            cartContent.products.map { (id, count) -> productRepository.getProductById(id) to count }
                .toMap()
        total = productItems.map { (product, count) -> product.price * count }.sum()
    }
}
