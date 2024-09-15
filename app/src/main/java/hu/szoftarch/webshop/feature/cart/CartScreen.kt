package hu.szoftarch.webshop.feature.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.szoftarch.webshop.ui.common.ProductCardWithAddRemove
import hu.szoftarch.webshop.ui.common.TextInput

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        cartViewModel.load()
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 96.dp)
        ) {
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

        CheckoutBox(modifier = Modifier.align(Alignment.BottomEnd))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CheckoutBox(
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(2.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "5000 HUF",
                fontWeight = FontWeight.Bold
            )

            Button(
                modifier = Modifier.weight(1.5f),
                onClick = { showBottomSheet = true }
            ) {
                Icon(
                    imageVector = Icons.Filled.Payment,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(text = "Check Out")
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            CheckoutBottomSheetContent()
        }
    }
}

@Composable
private fun CheckoutBottomSheetContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextInput(
            labelText = "Name",
            selectedText = "",
            onValueChange = { }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextInput(
            labelText = "Zip Code",
            selectedText = "",
            onValueChange = { }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextInput(
            labelText = "Country",
            selectedText = "",
            onValueChange = { }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextInput(
            labelText = "City",
            selectedText = "",
            onValueChange = { }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextInput(
            labelText = "Street",
            selectedText = "",
            onValueChange = { }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextInput(
            labelText = "Phone Number",
            selectedText = "",
            onValueChange = { }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextInput(
            labelText = "Email Address",
            selectedText = "",
            onValueChange = { }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ActionButtons(
            onDismiss = { },
            onPay = { }
        )
    }
}

@Composable
private fun ActionButtons(
    onDismiss: () -> Unit,
    onPay: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = onDismiss,
            content = { Text("Cancel") }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            modifier = Modifier.weight(1f),
            onClick = {
                onPay()
                onDismiss()
            },
            content = { Text("Pay") }
        )
    }
}
