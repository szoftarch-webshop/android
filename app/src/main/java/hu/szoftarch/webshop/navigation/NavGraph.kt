package hu.szoftarch.webshop.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hu.szoftarch.webshop.feature.camera.CameraScreen
import hu.szoftarch.webshop.feature.cart.CartScreen
import hu.szoftarch.webshop.feature.home.HomeScreen
import hu.szoftarch.webshop.feature.search.SearchScreen
import hu.szoftarch.webshop.ui.common.BottomNavBar
import hu.szoftarch.webshop.ui.common.NavigationItem

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(bottomBar = {
        BottomNavBar(
            navController.currentBackStackEntryAsState().value?.destination?.route
        ) { navController.navigate(it) }
    }) { padding ->
        NavHost(
            navController = navController, startDestination = NavigationItem.HOME.route
        ) {
            composable(
                route = NavigationItem.HOME.route
            ) {
                HomeScreen(padding)
            }

            composable(
                route = NavigationItem.SEARCH.route
            ) {
                SearchScreen(padding)
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
