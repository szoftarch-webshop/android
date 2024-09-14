package hu.szoftarch.webshop.feature.search
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.szoftarch.webshop.model.data.Product
import hu.szoftarch.webshop.model.repository.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



sealed class ProductListState {
    object Loading : ProductListState()
    data class Error(val error: Throwable) : ProductListState()
    //TODO List
    data class Result(val todoList : List<Product>) : ProductListState()
}

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel()
{
    private val _state = MutableStateFlow<ProductListState>(ProductListState.Loading)
    val state = _state.asStateFlow()


    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            try {
                _state.value = ProductListState.Loading
                delay(2000)
                _state.value = ProductListState.Result(productRepository.getProducts())
            } catch (e: Exception) {
                _state.value = ProductListState.Error(e)
            }
        }
    }
}