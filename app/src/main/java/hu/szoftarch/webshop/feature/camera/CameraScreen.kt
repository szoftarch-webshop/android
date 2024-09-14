package hu.szoftarch.webshop.feature.camera

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.szoftarch.webshop.ui.common.ProductCard

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    viewModel: CameraViewModel = hiltViewModel(),
    navController: NavController
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            viewModel.onPhotoTaken()
        } else {
            navController.popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.takePhoto(launcher)
    }

    LazyColumn(modifier = modifier) {
        item {
            viewModel.photo?.let {
                PaddedImage(
                    bitmap = it.asImageBitmap(), modifier = Modifier.padding(16.dp)
                )
            }

            HorizontalDivider(
                thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )
        }

        items(viewModel.numbers) {
            ProductCard(productName = it) {
                Text(text = "This is the product description")

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        TODO()
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add to cart",
                        modifier = Modifier.size(24.dp)
                    )

                    Text(text = "Add to cart")
                }
            }
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
            contentDescription = "Top Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        )
    }
}
