package hu.szoftarch.webshop.feature.camera

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.szoftarch.webshop.ui.common.ProductCardWithAddRemove

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    cameraViewModel: CameraViewModel = hiltViewModel(),
    navController: NavController
) {
    val pictureLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            cameraViewModel.onTakePicture()
        } else {
            navController.popBackStack()
        }
    }

    HandleCameraPermission(
        cameraViewModel = cameraViewModel,
        pictureLauncher = pictureLauncher,
        onPermissionGranted = {
            cameraViewModel.load(pictureLauncher)
        }
    )

    if (cameraViewModel.hasCameraPermission) {
        CameraContent(
            modifier = modifier,
            cameraViewModel = cameraViewModel
        )
    }
}


@Composable
fun HandleCameraPermission(
    cameraViewModel: CameraViewModel,
    pictureLauncher: ActivityResultLauncher<Uri>,
    onPermissionGranted: () -> Unit
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        cameraViewModel.onPermissionResult(isGranted, pictureLauncher)
    }

    LaunchedEffect(Unit) {
        if (!cameraViewModel.hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            onPermissionGranted()
        }
    }

    if (!cameraViewModel.hasCameraPermission) {
        PermissionDeniedView(
            onRequestPermission = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        )
    }
}

@Composable
fun PermissionDeniedView(onRequestPermission: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Camera permission is required to use this feature.",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRequestPermission) {
            Text(text = "Grant Permission and Take Picture")
        }
    }
}

@Composable
fun CameraContent(
    modifier: Modifier = Modifier,
    cameraViewModel: CameraViewModel
) {
    LazyColumn(modifier = modifier) {
        item {
            cameraViewModel.picture?.let {
                PaddedImage(
                    bitmap = it.asImageBitmap(), modifier = Modifier.padding(16.dp)
                )
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )
        }

        items(cameraViewModel.productItems.toList()) { (product, count) ->
            ProductCardWithAddRemove(
                productItem = product,
                productCount = count,
                onAdd = cameraViewModel::onAdd,
                onRemove = cameraViewModel::onRemove
            )
        }
    }
}


@Composable
fun PaddedImage(
    bitmap: ImageBitmap,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp)
    ) {
        Image(
            bitmap = bitmap,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        )
    }
}
