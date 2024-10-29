package hu.szoftarch.webshop.ui.common

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.szoftarch.webshop.feature.search.SearchViewModel
import hu.szoftarch.webshop.model.data.ProductItem

@Composable
fun ProductCardWithAddRemove(
    productItem: ProductItem,
    productCount: Int,
    expandedByDefault: Boolean = false,
    onExpansionChange: () -> Unit = {},
    onAdd: (ProductItem, Int) -> Unit,
    onRemove: (ProductItem, Int) -> Unit
) {
    var quantity by remember { mutableStateOf(1) }

    ProductCard(productItem, expandedByDefault, onExpansionChange = onExpansionChange) {
        Text(text = productItem.description)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Products in cart: $productCount")

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Products in stock: ${productItem.stock}")

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Weight: ${productItem.weight} kg")

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Material: ${productItem.material}")

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Categories: ${productItem.categoryNames.joinToString(", ")}")

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            IconButton(
                onClick = {
                    if (quantity > 1) quantity--
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Filled.Remove, contentDescription = "Decrease quantity")
            }

            Text(text = quantity.toString(), modifier = Modifier.align(Alignment.CenterVertically))

            IconButton(
                onClick = {
                    if (quantity < productItem.stock) quantity++
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Increase quantity")
            }
        }


        Button(
            onClick = {
               onAdd(productItem, quantity)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add to cart",
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "Add to cart")
        }

        Button(
            onClick = {
                onRemove(productItem, quantity)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Remove,
                contentDescription = "Remove from cart",
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "Remove from cart")
        }
    }
}
