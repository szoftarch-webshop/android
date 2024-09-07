package hu.szoftarch.webshop.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

@Composable
fun BottomNavBar(navController: NavController) {
    var selectedRoute by remember {
        mutableStateOf(NavigationItem.HOME.route)
    }

    NavigationBar {
        NavigationItem.entries.forEach { item ->
            NavigationBarItem(
                selected = item.route == selectedRoute,
                onClick = {
                    selectedRoute = item.route
                    navController.navigate(item.route)
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        imageVector = if (item.route == selectedRoute) {
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
