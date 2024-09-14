package hu.szoftarch.webshop.feature.search

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import hu.szoftarch.webshop.ui.common.ProductCardWithAddRemove

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        searchViewModel.load()
    }

    LazyColumn(modifier = modifier) {
        items(searchViewModel.productItems.toList()) { (product, count) ->
            ProductCardWithAddRemove(
                productItem = product,
                productCount = count,
                onAdd = searchViewModel::onAdd,
                onRemove = searchViewModel::onRemove
            )
        }
    }
}
