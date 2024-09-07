package hu.szoftarch.webshop.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hu.szoftarch.webshop.ui.common.BottomNavBar
import hu.szoftarch.webshop.ui.common.NavigationItem

@Composable
fun SearchScreen(navController: NavController) {
    Scaffold(bottomBar = {
        BottomNavBar(NavigationItem.SEARCH, navController)
    }) { padding ->
        Column(
            modifier = Modifier.padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Search Screen")
        }
    }
}
