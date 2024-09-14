package hu.szoftarch.webshop.feature.camera

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
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
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            cameraViewModel.onTakePicture()
        } else {
            navController.popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        cameraViewModel.load(launcher)
    }

    LazyColumn(modifier = modifier) {
        item {
            cameraViewModel.picture?.let {
                PaddedImage(
                    bitmap = it.asImageBitmap(), modifier = Modifier.padding(16.dp)
                )
            }

            HorizontalDivider(
                thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
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
