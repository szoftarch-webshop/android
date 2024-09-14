package hu.szoftarch.webshop.feature.camera

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun CameraScreen(
    navController: NavController,
    padding: PaddingValues,
    cameraViewModel: CameraViewModel = hiltViewModel()
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            cameraViewModel.handleCapturedPhoto()
        } else {
            navController.popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        cameraViewModel.takePhoto(launcher)
    }

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            cameraViewModel.photo?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                cameraViewModel.numbers.forEach { Text(text = it) }
            }
        }
    }
}
