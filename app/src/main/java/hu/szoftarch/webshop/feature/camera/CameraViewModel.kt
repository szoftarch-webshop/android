package hu.szoftarch.webshop.feature.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.szoftarch.webshop.feature.common.ProductManagementViewModel
import hu.szoftarch.webshop.model.data.CartContent
import hu.szoftarch.webshop.model.data.ProductItem
import hu.szoftarch.webshop.model.repository.CartRepository
import hu.szoftarch.webshop.model.repository.ProductRepository
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val serialNumberRecognitionService: SerialNumberRecognitionService,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ProductManagementViewModel(cartRepository, productRepository) {
    var hasCameraPermission by mutableStateOf(
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    )
        private set

    var picture by mutableStateOf<Bitmap?>(null)
        private set

    private var pictureUri by mutableStateOf<Uri?>(null)

    fun onPermissionResult(isGranted: Boolean, launcher: ActivityResultLauncher<Uri>) {
        hasCameraPermission = isGranted
        if (isGranted) {
            load(launcher)
        }
    }

    fun load(launcher: ActivityResultLauncher<Uri>) {
        if (pictureUri != null) {
            return
        }
        pictureUri = FileUtils.createImageUri(context)
        pictureUri?.let { launcher.launch(it) }
    }

    fun onTakePicture() = viewModelScope.launch {
        pictureUri?.let {
            val inputStream: InputStream? = context.contentResolver.openInputStream(pictureUri!!)
            inputStream?.let {
                val bitmap = BitmapFactory.decodeStream(it)
                picture = bitmap.rotateIfRequired(context, pictureUri!!)
                val serialNumbers = serialNumberRecognitionService.getSerialNumbers(bitmap)
                try {
                    val recognizedProducts =
                        serialNumbers.map { sn -> productRepository.getProductBySerialNumber(sn) }
                    productItems =
                        cartRepository.getProductCount(recognizedProducts.map { product -> product.id })
                            .map { (id, count) -> productRepository.getProductById(id) to count }
                            .toMap()
                } catch (e: NoSuchElementException) {
                    Log.i("CameraViewModel", "No product found for serial numbers: $serialNumbers")
                }
            }
        }
    }
}

private object FileUtils {
    fun createImageUri(context: Context): Uri {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photo.jpg")
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }
}

private fun Bitmap.rotateIfRequired(context: Context, uri: Uri): Bitmap {
    val inputStream = context.contentResolver.openInputStream(uri)
    val exif = ExifInterface(inputStream!!)
    val orientation =
        exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    val matrix = Matrix()

    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
    }

    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}
