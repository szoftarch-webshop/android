package hu.szoftarch.webshop.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.szoftarch.webshop.model.data.CategoryItem
import hu.szoftarch.webshop.model.data.ProductItem
import hu.szoftarch.webshop.model.data.ProductRetrievalOptions
import hu.szoftarch.webshop.model.repository.CartRepository
import hu.szoftarch.webshop.model.repository.CategoryRepository
import hu.szoftarch.webshop.model.repository.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {
    var productItems by mutableStateOf<Map<ProductItem, Int>>(mapOf())
        private set

    var availableCategories by mutableStateOf<List<CategoryItem>>(listOf())
        private set

    var options by mutableStateOf(ProductRetrievalOptions())
        private set

    fun load() = viewModelScope.launch {
        val paginatedProducts = productRepository.getProducts(options)
        val ids = paginatedProducts.products.map { it.id }
        val inCart = cartRepository.getProductCount(ids)
        productItems =
            inCart.map { (id, count) -> productRepository.getProductById(id) to count }.toMap()
        availableCategories = categoryRepository.getCategories()
    }

    fun onAdd(productId: Int) = viewModelScope.launch {
        val cart = cartRepository.addToCart(productId)
        productItems = productItems.toMutableMap().apply {
            val product = productRepository.getProductById(productId)
            this[product] = cart.products[productId] ?: 0
        }
    }

    fun onRemove(productId: Int) = viewModelScope.launch {
        val cart = cartRepository.removeFromCart(productId)
        productItems = productItems.toMutableMap().apply {
            val product = productRepository.getProductById(productId)
            this[product] = cart.products[productId] ?: 0
        }
    }

    fun onApplyOptions(newOptions: ProductRetrievalOptions) = viewModelScope.launch {
        options = newOptions
        val paginatedProducts = productRepository.getProducts(options)
        productItems = cartRepository.getProductCount(paginatedProducts.products.map { it.id })
            .map { (id, count) -> productRepository.getProductById(id) to count }.toMap()
    }
}
