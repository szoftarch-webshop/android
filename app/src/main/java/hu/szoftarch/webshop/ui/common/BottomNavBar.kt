package hu.szoftarch.webshop.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomNavBar(selectedRoute: String?, onNavItemSelected: (String) -> Unit) {
    NavigationBar {
        NavigationItem.entries.forEach { item ->
            NavigationBarItem(selected = item.route == selectedRoute, onClick = {
                onNavItemSelected(item.route)
            }, label = {
                Text(text = item.title)
            }, icon = {
                Icon(
                    imageVector = if (item.route == selectedRoute) {
                        item.selectedIcon
                    } else {
                        item.unselectedIcon
                    }, contentDescription = item.title
                )
            })
        }
    }
}
