package hu.szoftarch.webshop.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.szoftarch.webshop.feature.home.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen()
        }

        composable(
            route = Screen.Search.route
        ) {
            // Search Screen
            TODO()
        }

        composable(
            route = Screen.Camera.route
        ) {
            // Camera Screen
            TODO()
        }

        composable(
            route = Screen.Cart.route
        ) {
            // Cart Screen
            TODO()
        }
    }
}
