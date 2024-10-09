package hu.szoftarch.webshop.feature.splashscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun SplashScreen(viewModel: SplashViewModel, onSplashFinished: @Composable () -> Unit) {
    val isSplashVisible by viewModel.isSplashShow.collectAsState()

    if (isSplashVisible) {

    } else {
        onSplashFinished()
    }
}