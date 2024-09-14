package hu.szoftarch.webshop.feature.cart

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import hu.szoftarch.webshop.ui.common.ProductCardWithAddRemove

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        cartViewModel.load()
    }

    LazyColumn(modifier = modifier) {
        items(cartViewModel.productItems.toList()) { (product, count) ->
            ProductCardWithAddRemove(
                productItem = product,
                productCount = count,
                expandedByDefault = true,
                onAdd = cartViewModel::onAdd,
                onRemove = cartViewModel::onRemove
            )
        }
    }
}
