package hu.szoftarch.webshop.feature.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.szoftarch.webshop.model.data.Product
import hu.szoftarch.webshop.model.repository.CartRepository
import hu.szoftarch.webshop.model.repository.ProductRepository
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val textRecognitionService: TextRecognitionService,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {
    var photo by mutableStateOf<Bitmap?>(null)
        private set

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    var productCount by mutableStateOf<Map<String, Int>>(emptyMap())
        private set

    private var photoUri by mutableStateOf<Uri?>(null)

    fun onPhotoTaken() {
        photoUri?.let {
            val inputStream: InputStream? = context.contentResolver.openInputStream(photoUri!!)
            inputStream?.let {
                val bitmap = BitmapFactory.decodeStream(it)
                photo = bitmap.rotateIfRequired(context, photoUri!!)
                viewModelScope.launch {
                    val serialNumbers = textRecognitionService.getSerialNumbers(bitmap)

                    products = serialNumbers.mapNotNull { serialNumber ->
                        productRepository.getProductBySerialNumber(serialNumber)
                    }

                    productCount = products.associate { product ->
                        product.id to cartRepository.productCount(product.id)
                    }
                }
            }
        }
    }

    fun takePhoto(launcher: ActivityResultLauncher<Uri>) {
        if (photoUri != null) {
            return
        }
        photoUri = FileUtils.createImageUri(context)
        photoUri?.let { launcher.launch(it) }
    }

    fun addToCart(productId: String) {
        viewModelScope.launch {
            cartRepository.addToCart(productId)
            productCount = productCount.toMutableMap().apply {
                this[productId] = cartRepository.productCount(productId)
            }
        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            cartRepository.removeFromCart(productId)
            productCount = productCount.toMutableMap().apply {
                this[productId] = cartRepository.productCount(productId)
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
