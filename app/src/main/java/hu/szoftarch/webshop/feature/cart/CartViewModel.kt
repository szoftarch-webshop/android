package hu.szoftarch.webshop.feature.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.szoftarch.webshop.model.data.ProductItem
import hu.szoftarch.webshop.model.repository.CartRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    var productItems by mutableStateOf<Map<ProductItem, Int>>(mapOf())
        private set

    fun load() = viewModelScope.launch {
        productItems = cartRepository.getProductsInCart()
    }

    fun onAdd(product: ProductItem) = viewModelScope.launch {
        productItems = cartRepository.addToCart(product)
    }

    fun onRemove(product: ProductItem) = viewModelScope.launch {
        productItems = cartRepository.removeFromCart(product)
    }
}
