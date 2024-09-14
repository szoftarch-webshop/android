package hu.szoftarch.webshop.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.szoftarch.webshop.model.data.ProductItem
import hu.szoftarch.webshop.model.repository.CartRepository
import hu.szoftarch.webshop.model.repository.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {
    var productItems by mutableStateOf<Map<ProductItem, Int>>(mapOf())
        private set

    fun load() = viewModelScope.launch {
        val products = productRepository.getProducts()
        productItems = cartRepository.getProductCount(products)
    }

    fun onAdd(product: ProductItem) = viewModelScope.launch {
        val cart = cartRepository.addToCart(product)
        productItems = productItems.toMutableMap().apply {
            this[product] = cart[product] ?: 0
        }
    }

    fun onRemove(product: ProductItem) = viewModelScope.launch {
        val cart = cartRepository.removeFromCart(product)
        productItems = productItems.toMutableMap().apply {
            this[product] = cart[product] ?: 0
        }
    }
}
