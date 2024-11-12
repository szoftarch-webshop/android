package hu.szoftarch.webshop.feature.common

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.szoftarch.webshop.model.data.CartContent
import hu.szoftarch.webshop.model.data.ProductItem
import hu.szoftarch.webshop.model.repository.CartRepository
import hu.szoftarch.webshop.model.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class ProductManagementViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    var productItems by mutableStateOf<Map<ProductItem, Int>>(mapOf())
        protected set

    private val productCache = mutableMapOf<Int, ProductItem>()

    var toast: Toast? = null

    fun onAdd(context: Context, product: ProductItem, quantity: Int) {
        var message: String
        val productInCart = getProductQuantity(product)
        val newQuantity = productInCart + quantity

        message = if (newQuantity < 0 || newQuantity > product.stock) {
            "Cannot add $quantity ${product.name}. ${product.stock - productInCart} left in stock."
        } else {
            "You have added $quantity ${product.name} to your cart."
        }

        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()

        if (newQuantity <= product.stock) {
            viewModelScope.launch(Dispatchers.IO) {
                val cartContent = cartRepository.addToCart(product.id, quantity)
                updateProductItems(cartContent, product.id)
            }
        }
    }

    fun onRemove(context: Context, product: ProductItem, quantity: Int) {
        val productInCart = getProductQuantity(product)
        val newQuantity = productInCart - quantity

        val message = if (newQuantity < 0) {
            "Cannot remove more ${product.name} than what is in your cart."
        } else {
            "You have removed $quantity ${product.name} from your cart."
        }

        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()

        if (newQuantity >= 0) {
            viewModelScope.launch {
                val cartContent = cartRepository.removeFromCart(product.id, quantity)
                updateProductItems(cartContent, product.id) // Implement this in subclasses
            }
        }
    }

    open fun getProductQuantity(product: ProductItem): Int {
        return productItems[product] ?: 0
    }
    open suspend fun updateProductItems(cartContent: CartContent, productId: Int) {
        val product = productCache[productId] ?: productRepository.getProductById(productId).also {
            productCache[productId] = it
        }

        val updatedQuantity = cartContent.products[productId] ?: 0

        productItems = productItems.toMutableMap().apply {
            this[product] = updatedQuantity
        }.toMap()
    }
}