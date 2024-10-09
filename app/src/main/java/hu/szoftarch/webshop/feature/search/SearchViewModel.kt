package hu.szoftarch.webshop.feature.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.szoftarch.webshop.model.data.CartContent
import hu.szoftarch.webshop.model.data.CategoryItem
import hu.szoftarch.webshop.model.data.ProductItem
import hu.szoftarch.webshop.model.data.ProductRetrievalOptions
import hu.szoftarch.webshop.model.repository.CartRepository
import hu.szoftarch.webshop.model.repository.CategoryRepository
import hu.szoftarch.webshop.model.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {
    var productItems by mutableStateOf<Map<ProductItem, Int>>(mapOf())
        private set

    var productCardState by mutableStateOf<Map<Int, Boolean>>(mapOf())
        private set

    var options by mutableStateOf(ProductRetrievalOptions(pageNumber = 1, pageSize = 3))
        private set

    val availableCategories = mutableStateOf<List<CategoryItem>>(listOf())

    fun load() = viewModelScope.launch {
        productItems = getMatchingProductsWithCountInCart()
        availableCategories.value = categoryRepository.getCategories()
    }

    fun onAdd(productId: Int) = viewModelScope.launch {
        val cartContent = cartRepository.addToCart(productId)
        updateProductItems(cartContent, productId)
    }

    fun onRemove(productId: Int) = viewModelScope.launch {
        val cartContent = cartRepository.removeFromCart(productId)
        updateProductItems(cartContent, productId)
    }

    fun onApplyOptions(newOptions: ProductRetrievalOptions) = viewModelScope.launch {
        options = newOptions
        productItems = getMatchingProductsWithCountInCart()
    }

    fun onBottomReached() = viewModelScope.launch {
        getMatchingProductsWithCountInCart().takeIf { it.isNotEmpty() }?.let { newProducts ->
            options = options.copy(pageNumber = options.pageNumber + 1)
            productItems = productItems + newProducts
        } ?: run {
            options = options.copy(pageNumber = options.pageNumber - 1)
        }
    }

    fun onChangeProductCardState(productId: Int) {
        productCardState = productCardState.toMutableMap().apply {
            this[productId] = !(productCardState[productId] ?: false)
        }.toMap()
    }

    private suspend fun getMatchingProductsWithCountInCart(): Map<ProductItem, Int>
    = withContext(Dispatchers.IO) {
        try {
            val paginatedProducts = productRepository.getProducts(options)

            val productIds = paginatedProducts.products.map { it.id }
            val productCount = cartRepository.getProductCount(productIds)

            val products = paginatedProducts.products.associateBy { it.id }
            val result = productCount.mapNotNull { (id, count) ->
                products[id]?.let { product -> product to count }
            }.toMap()
            result
        } catch (e: Exception) {
            Log.e("ProductRepository", "Failed to fetch matching products with count in cart", e)
            emptyMap()
        }
    }


    private suspend fun updateProductItems(cartContent: CartContent, productId: Int) {
        productItems = productItems.toMutableMap().apply {
            val product = productRepository.getProductById(productId)
            this[product] = cartContent.products[productId] ?: 0
        }
    }
}
