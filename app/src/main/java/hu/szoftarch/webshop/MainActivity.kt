package hu.szoftarch.webshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import hu.szoftarch.webshop.navigation.NavGraph
import hu.szoftarch.webshop.ui.theme.WebshopTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WebshopTheme {
                NavGraph()
            }
        }
    }
}
