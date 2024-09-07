package hu.szoftarch.webshop.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomNavBar(activeItem: NavigationItem, navController: NavController) {
    NavigationBar {
        NavigationItem.entries.forEach { item ->
            NavigationBarItem(
                selected = item.route == activeItem.route,
                onClick = {
                    navController.navigate(item.route)
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        imageVector = if (item.title == activeItem.title) {
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        },
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}
