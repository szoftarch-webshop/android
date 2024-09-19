package hu.szoftarch.webshop.feature.search

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.szoftarch.webshop.model.data.CategoryItem
import hu.szoftarch.webshop.model.data.ProductRetrievalOptions
import hu.szoftarch.webshop.ui.common.ProductCardWithAddRemove
import hu.szoftarch.webshop.ui.common.TextInput
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        searchViewModel.load()
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(searchViewModel.productItems.toList()) { (product, count) ->
                ProductCardWithAddRemove(
                    productItem = product,
                    productCount = count,
                    expandedByDefault = searchViewModel.productCardState[product.id] ?: false,
                    onExpansionChange = { searchViewModel.onChangeProductCardState(product.id) },
                    onAdd = { searchViewModel.onAdd(product.id) },
                    onRemove = { searchViewModel.onRemove(product.id) }
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
        var selectedCategoryName by remember { mutableStateOf(options.category) }

        options.searchString?.let {
            TextInput(
                selectedText = it,
                labelText = "Name or Serial Number",
                onValueChange = { nameOrSerialNumber = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        options.material?.let {
            TextInput(
                selectedText = it,
                labelText = "Material",
                onValueChange = { material = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CategorySelector(
            categories = categories,
            selectedCategoryName = selectedCategoryName,
            onCategorySelected = { selectedCategoryName = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ActionButtons(
            onDismiss = onDismiss,
            onApplyFilter = onApplyFilter,
            options = ProductRetrievalOptions(
                searchString = nameOrSerialNumber,
                material = material,
                category = selectedCategoryName
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategorySelector(
    categories: List<CategoryItem>,
    selectedCategoryName: String?,
    onCategorySelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedCategory = categories.firstOrNull { it.name == selectedCategoryName }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            readOnly = true,
            value = selectedCategory?.name ?: "",
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
                        onCategorySelected(category.name)
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
