package hu.szoftarch.webshop.feature.camera

import android.graphics.Bitmap
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.tasks.await

interface SerialNumberRecognitionService {
    suspend fun getSerialNumbers(bitmap: Bitmap): Set<String>
}

object SerialNumberRecognitionServiceImpl : SerialNumberRecognitionService {
    override suspend fun getSerialNumbers(bitmap: Bitmap) =
        try {
            val image = InputImage.fromBitmap(bitmap, 0)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val result: Text = recognizer.process(image).await()

            val serialNumberPattern = "\\b\\d{10}\\b".toRegex()
            val recognizedText = result.text

            Log.d("SerialNumberRecognitionServiceImpl", "Recognized text: $recognizedText")

            val serialNumbers = serialNumberPattern.findAll(recognizedText).map { it.value }.toSet()

            Log.d("SerialNumberRecognitionServiceImpl", "Recognized serial numbers: $serialNumbers")

            serialNumbers
        } catch (e: Exception) {
            Log.e("SerialNumberRecognitionServiceImpl", "Error recognizing text", e)
            setOf()
        }
}

@Module
@InstallIn(SingletonComponent::class)
object SerialNumberRecognitionModule {
    @Provides
    fun serialNumberRecognitionService(): SerialNumberRecognitionService =
        SerialNumberRecognitionServiceImpl
}
