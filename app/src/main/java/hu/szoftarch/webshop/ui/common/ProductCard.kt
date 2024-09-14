package hu.szoftarch.webshop.ui.common

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import hu.szoftarch.webshop.model.data.ProductItem

@Composable
fun ProductCard(
    productItem: ProductItem,
    expandedByDefault: Boolean = false,
    expandedContent: @Composable () -> Unit
) {
    var expanded by remember {
        mutableStateOf(expandedByDefault)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            expanded = !expanded
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = productItem.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Inside
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = productItem.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = productItem.priceHuf(), fontSize = 18.sp)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "(${productItem.serialNumber})", fontSize = 10.sp, color = Color.LightGray)

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                expandedContent()
            }
        }
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewProductCard() {
    ProductCard(productItem = ProductItem(),
        expandedByDefault = false,
        expandedContent = {
            Text(text = "This is the product description")

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    TODO()
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add to cart",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Add to cart")
            }
        })
}
