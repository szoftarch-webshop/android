package hu.szoftarch.webshop.feature.cart

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor() : ViewModel() {

    private val _paymentIntentReceived = MutableStateFlow(false)
    val paymentIntentReceived: StateFlow<Boolean> get() = _paymentIntentReceived
    private val _savedUri = MutableStateFlow<Uri?>(null)
    val savedUri: StateFlow<Uri?> = _savedUri

    fun saveUri(uri: Uri?) {
        _savedUri.value = uri
    }

    fun handleIntentData(uri: Uri?) {
        if (uri?.scheme == "webshop"&& uri.host == "payment" && uri.path == "/callback") {
            _paymentIntentReceived.value = true
            saveUri(uri)
            Log.d("PaymentViewModel", "Payment callback detected, state updated to true")
        }
    }

    fun markPaymentIntentHandled() {
        _paymentIntentReceived.value = false
        Log.d("PaymentViewModel", "Payment intent marked as handled")
    }
}