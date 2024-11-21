package hu.szoftarch.webshop

import android.net.Uri
import android.os.Bundle
import android.util.Log
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
        val intentUri = intent?.data
        Log.d("MainActivity", "onCreate called with URI: $intentUri")
        setContent {
            WebshopTheme {
                NavGraph(startingIntentUri = intentUri)
            }
        }
    }
}
