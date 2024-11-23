package hu.szoftarch.webshop.feature.search

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.szoftarch.webshop.feature.cart.CartViewModel
import hu.szoftarch.webshop.feature.cart.PaymentViewModel
import hu.szoftarch.webshop.model.data.CategoryItem
import hu.szoftarch.webshop.model.data.ProductRetrievalOptions
import hu.szoftarch.webshop.ui.common.ProductCardWithAddRemove
import hu.szoftarch.webshop.ui.common.TextInput
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    startingIntentUri: Uri?,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel(),
    paymentViewModel: PaymentViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val paymentIntentReceived by paymentViewModel.paymentIntentReceived.collectAsState()
    val savedUri by paymentViewModel.savedUri.collectAsState()
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        searchViewModel.load()
        Log.d("CartScreen", "LaunchedEffect UNIT triggered, paymentIntentReceived: $paymentIntentReceived")

    }
    LaunchedEffect(startingIntentUri) {
        Log.d("SearchScreen", "Handling starting URI: $startingIntentUri")
        paymentViewModel.handleIntentData(startingIntentUri)
    }

    LaunchedEffect(paymentIntentReceived) {
        Log.d(
            "SearchScreen",
            "LaunchedEffect triggered, paymentIntentReceived: $paymentIntentReceived"
        )
        if (paymentIntentReceived) {
            cartViewModel.checkPaymentStatus(
                uri = savedUri,
                onSuccess = { status ->
                    Log.d("SearchScreen", "Payment status received: $status")
                    dialogTitle = "Payment Status"
                    dialogMessage =
                        "Your payment was successful, the product is now on its way to you. Thank you for purchasing!"
                    showDialog = true
                    isError = false

                },
                onError = { error ->
                    Log.e("SearchScreen", "Error checking payment status: $error")
                    dialogTitle = "Payment Status"
                    dialogMessage =
                        "An error occured during the payment. Contact us for details!"
                    showDialog = true
                    isError = true

                }
            )
            paymentViewModel.markPaymentIntentHandled()
            cartViewModel.clearCart()
        }
    }
    if (showDialog) {
        PaymentAlertDialog(
            title = dialogTitle,
            text = dialogMessage,
            isError = isError,
            onDismiss = { showDialog = false }
        )
    }


    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(searchViewModel.productItems.toList()) { (product, count) ->
                ProductCardWithAddRemove(
                    productItem = product,
                    productCount = count,
                    expandedByDefault = searchViewModel.productCardState[product.id] ?: false,
                    onExpansionChange = { searchViewModel.onChangeProductCardState(product.id) },
                    onAdd = { productItem, quantity ->
                        searchViewModel.onAdd(context, productItem, quantity)
                    },
                    onRemove = { productItem, quantity->
                        searchViewModel.onRemove(context, productItem, quantity)
                    }
                )
            }

            item {
                LaunchedEffect(Unit) {
                    searchViewModel.onBottomReached()
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        FloatingActionButton(
            onClick = { showBottomSheet = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.FilterList, contentDescription = "Filter")
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }, sheetState = sheetState
        ) {
            FilterBottomSheetContent(options = searchViewModel.options,
                categories = searchViewModel.availableCategories.value,
                onApplyFilter = searchViewModel::onApplyOptions,
                onDismiss = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                })
        }
    }
}

@Composable
fun PaymentAlertDialog(
    title: String,
    text: String,
    isError: Boolean,
    onDismiss: () -> Unit
) {
    var openDialog by remember { mutableStateOf(true) }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (isError) Icons.Default.Error else Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = if (isError) Color.Red else Color.Green,
                        modifier = Modifier.size(24.dp).padding(end = 8.dp)
                    )
                    Text(title, fontSize = 18.sp)
                }
            },
            text = { Text(text) },
            confirmButton = {
                TextButton(
                    onClick = { openDialog = false },
                    content = { Text("OK") }
                )
            },
            modifier = Modifier.shadow(4.dp, shape = RoundedCornerShape(16.dp))
        )
    }
}

@Composable
private fun FilterBottomSheetContent(
    options: ProductRetrievalOptions,
    categories: List<CategoryItem>,
    onDismiss: () -> Unit,
    onApplyFilter: (ProductRetrievalOptions) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        var nameOrSerialNumber by remember { mutableStateOf(options.searchString) }
        var material by remember { mutableStateOf(options.material) }
        var selectedCategoryId by remember { mutableStateOf(options.categoryId) }

        TextInput(
            selectedText = options.searchString ?: "",
            labelText = "Name or Serial Number",
            onValueChange = { nameOrSerialNumber = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextInput(
            selectedText = options.material ?: "",
            labelText = "Material",
            onValueChange = { material = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CategorySelector(
            categories = categories,
            selectedCategoryId = selectedCategoryId,
            onCategorySelected = { selectedCategoryId = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ActionButtons(
            onDismiss = onDismiss,
            onApplyFilter = onApplyFilter,
            options = ProductRetrievalOptions(
                searchString = nameOrSerialNumber,
                material = material,
                categoryId = selectedCategoryId
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategorySelector(
    categories: List<CategoryItem>,
    selectedCategoryId: Int?,
    onCategorySelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedCategory = categories.firstOrNull { it.id == selectedCategoryId }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            readOnly = true,
            value = selectedCategory?.name ?: "Select Category",
            onValueChange = {},
            label = { Text("Category") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(text = category.name) },
                    onClick = {
                        onCategorySelected(category.id)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
private fun ActionButtons(
    onDismiss: () -> Unit,
    onApplyFilter: (ProductRetrievalOptions) -> Unit,
    options: ProductRetrievalOptions
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
                onApplyFilter(options)
                onDismiss()
            },
            content = { Text("Apply") }
        )
    }
}
