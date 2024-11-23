package hu.szoftarch.webshop.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    navController: NavHostController = rememberNavController(),
    startingIntentUri: Uri?
) {
    var currentUri: Uri? by remember { mutableStateOf(startingIntentUri) }

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
                val uriForSearchScreen = currentUri
                if (uriForSearchScreen != null) {
                    SearchScreen(modifier = Modifier.padding(padding), startingIntentUri = uriForSearchScreen)
                    currentUri = null
                } else {
                    SearchScreen(modifier = Modifier.padding(padding), startingIntentUri = null)
                }
            }

            composable(
                route = NavigationItem.CAMERA.route
            ) {
                CameraScreen(modifier = Modifier.padding(padding), navController = navController)
            }

            composable(
                route = NavigationItem.CART.route
            ) {
                CartScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }
        }
    }
}
