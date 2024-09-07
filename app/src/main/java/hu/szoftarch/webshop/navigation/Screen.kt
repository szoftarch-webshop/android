package hu.szoftarch.webshop.navigation

sealed class Screen(val route: String) {
    data object Home : Screen(route = "home")
    data object Search : Screen(route = "search")
    data object Camera : Screen(route = "camera")
    data object Cart : Screen(route = "cart")
}