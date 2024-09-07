package hu.szoftarch.webshop.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.szoftarch.webshop.feature.camera.CameraScreen
import hu.szoftarch.webshop.feature.cart.CartScreen
import hu.szoftarch.webshop.feature.home.HomeScreen
import hu.szoftarch.webshop.feature.search.SearchScreen
import hu.szoftarch.webshop.ui.common.NavigationItem

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.HOME.route
    ) {
        composable(
            route = NavigationItem.HOME.route
        ) {
            HomeScreen(navController)
        }

        composable(
            route = NavigationItem.SEARCH.route
        ) {
            SearchScreen(navController)
        }

        composable(
            route = NavigationItem.CAMERA.route
        ) {
            CameraScreen(navController)
        }

        composable(
            route = NavigationItem.CART.route
        ) {
            CartScreen(navController)
        }
    }
}
