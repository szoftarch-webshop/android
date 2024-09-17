package hu.szoftarch.webshop.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun TextInput(
    selectedText: String,
    labelText: String,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(selectedText) }

    OutlinedTextField(
        value = text,
        singleLine = true,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        label = { Text(labelText) },
        modifier = Modifier.fillMaxWidth()
    )
}
