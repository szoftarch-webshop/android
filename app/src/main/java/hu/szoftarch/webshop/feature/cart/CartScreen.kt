package hu.szoftarch.webshop.feature.cart

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import hu.szoftarch.webshop.ui.common.ProductCardWithAddRemove

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(viewModel.products) { product ->
            ProductCardWithAddRemove(
                product,
                expandedByDefault = true,
                getProductCount = { viewModel.productCount[product.id] },
                onAdd = { viewModel.addToCart(product.id) },
                onRemove = { viewModel.removeFromCart(product.id) }
            )
        }
    }
}
