package hu.szoftarch.webshop.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hu.szoftarch.webshop.feature.camera.CameraScreen
import hu.szoftarch.webshop.feature.cart.CartScreen
import hu.szoftarch.webshop.feature.search.SearchScreen
import hu.szoftarch.webshop.ui.common.BottomNavBar
import hu.szoftarch.webshop.ui.common.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Webshop")
                }
            )
        },
        bottomBar = {
            BottomNavBar(
                navController.currentBackStackEntryAsState().value?.destination?.route
            ) { navController.navigate(it) }
        }) { padding ->
        NavHost(
            navController = navController, startDestination = NavigationItem.SEARCH.route
        ) {
            composable(
                route = NavigationItem.SEARCH.route
            ) {
                SearchScreen(modifier = Modifier.padding(padding))
            }

            composable(
                route = NavigationItem.CAMERA.route
            ) {
                CameraScreen(modifier = Modifier.padding(padding), navController = navController)
            }

            composable(
                route = NavigationItem.CART.route
            ) {
                CartScreen(modifier = Modifier.padding(padding))
            }
        }
    }
}
