package hu.szoftarch.webshop.feature.camera

import android.graphics.Bitmap
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface SerialNumberRecognitionService {
    suspend fun recognizeNumbers(bitmap: Bitmap): List<String>
}

class SerialNumberRecognitionServiceImpl @Inject constructor() : SerialNumberRecognitionService {
    override suspend fun recognizeNumbers(bitmap: Bitmap): List<String> {
        val image = InputImage.fromBitmap(bitmap, 0)

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        val result: Text = recognizer.process(image).await()

        val serialNumberPattern = "\\b\\d{10}\\b".toRegex()

        val recognizedText = result.text
        Log.d("NumberRecognitionServiceImpl", "Recognized text: $recognizedText")

        val serialNumbers = serialNumberPattern.findAll(recognizedText).map { it.value }.toList()

        Log.d("NumberRecognitionServiceImpl", "Recognized serial numbers: $serialNumbers")
        return serialNumbers
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NumberRecognitionModule {
    @Binds
    abstract fun numberRecognitionService(
        impl: SerialNumberRecognitionServiceImpl
    ): SerialNumberRecognitionService
}
