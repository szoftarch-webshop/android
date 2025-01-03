package hu.szoftarch.webshop.feature.cart

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.szoftarch.webshop.ui.common.ProductCardWithAddRemove
import hu.szoftarch.webshop.ui.common.TextInput

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel = hiltViewModel(),
    customerInfoViewModel: CustomerInfoViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        cartViewModel.load()
    }
    val ctx = LocalContext.current
    LaunchedEffect(Unit) {
        customerInfoViewModel.onPaymentRequest = { customerInfo ->
            cartViewModel.initiatePaymentWithCustomerInfo(
                customerInfo,
                onSuccess = { paymentUrl ->
                    if (paymentUrl.isNotBlank() && paymentUrl.startsWith("http")) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(paymentUrl))
                        ctx.startActivity(intent)
                    }
                    else{
                        Toast.makeText(ctx, "Érvénytelen URL: $paymentUrl", Toast.LENGTH_LONG).show()
                    }
                },
                onError = { errorMessage ->
                    Toast.makeText(ctx, "Hiba: $errorMessage", Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    Box(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(cartViewModel.productItems.toList()) { (product, count) ->
                ProductCardWithAddRemove(
                    productItem = product,
                    productCount = count,
                    expandedByDefault = true,
                    onAdd = { productItem, quantity ->
                        cartViewModel.onAdd(productItem.id, quantity)
                    },
                    onRemove = { productItem, quantity  ->
                        cartViewModel.onRemove(productItem.id, quantity)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        CheckoutButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(
                    bottom = 16.dp,
                    start = 32.dp,
                    end = 32.dp
                ),
            total = cartViewModel.total,
            onClearCart = { cartViewModel.clearCart() }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CheckoutButton(
    modifier: Modifier = Modifier,
    total: Int,
    onClearCart: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onClearCart,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB42147)
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
        }

        Button(
            onClick = { showBottomSheet = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF198605)
            )
        ) {
            Text(
                text = "$total HUF",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.Payment,
                contentDescription = "Check Out",

            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Check Out")
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
private fun CheckoutBottomSheetContent(customerInfoViewModel: CustomerInfoViewModel = hiltViewModel()) {
    val fields = listOf(
        Triple(
            "Name",
            customerInfoViewModel.uiState.nameError
        ) { it: String -> customerInfoViewModel.onNameChanged(it) },
        Triple(
            "Zip Code",
            customerInfoViewModel.uiState.zipCodeError
        ) { it: String -> customerInfoViewModel.onZipCodeChanged(it) },
        Triple(
            "Country",
            customerInfoViewModel.uiState.countryError
        ) { it: String -> customerInfoViewModel.onCountryChanged(it) },
        Triple(
            "City",
            customerInfoViewModel.uiState.cityError
        ) { it: String -> customerInfoViewModel.onCityChanged(it) },
        Triple(
            "Street",
            customerInfoViewModel.uiState.streetError
        ) { it: String -> customerInfoViewModel.onStreetChanged(it) },
        Triple(
            "Phone Number",
            customerInfoViewModel.uiState.phoneNumberError
        ) { it: String -> customerInfoViewModel.onPhoneNumberChanged(it) },
        Triple(
            "Email Address",
            customerInfoViewModel.uiState.emailAddressError
        ) { it: String -> customerInfoViewModel.onEmailAddressChanged(it) }
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(fields) { field ->
            TextInputWithSpacer(
                labelText = field.first,
                isError = field.second != null,
                errorMessage = field.second ?: "",
                onValueChange = field.third
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            ActionButtons(
                onDismiss = { },
                onPay = {
                    customerInfoViewModel.validateAndRequestPayment()
                }
            )
        }
    }
}

@Composable
private fun TextInputWithSpacer(
    labelText: String,
    isError: Boolean,
    errorMessage: String = "",
    onValueChange: (String) -> Unit
) {
    Column {
        TextInput(
            labelText = labelText,
            selectedText = "",
            isError = isError,
            errorMessage = errorMessage,
            onValueChange = onValueChange
        )
        Spacer(modifier = Modifier.height(16.dp))
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
