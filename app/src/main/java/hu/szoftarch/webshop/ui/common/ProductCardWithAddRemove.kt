package hu.szoftarch.webshop.ui.common

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.szoftarch.webshop.model.data.Product

@Composable
fun ProductCardWithAddRemove(
    product: Product,
    expandedByDefault: Boolean = false,
    getProductCount: (String) -> Int?,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    ProductCard(product, expandedByDefault) {
        Text(text = product.description)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Products in cart: ${getProductCount(product.id)}")

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = {
                onAdd()
            }, modifier = Modifier
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
                onRemove()
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
