package hu.szoftarch.webshop.model.datasource.impl

import android.net.Uri
import android.util.Log
import hu.szoftarch.webshop.model.api.ApiService
import hu.szoftarch.webshop.model.api.BarionApiService
import hu.szoftarch.webshop.model.data.PaymentDetails
import hu.szoftarch.webshop.model.service.PaymentService
import javax.inject.Inject

class PaymentServiceImpl @Inject constructor(
    private val apiService: ApiService,
    private val barionApiService: BarionApiService,
) : PaymentService {

    private var paymentId: String? = null

    override fun setPaymentIdFromUri(uri: Uri?) {
        paymentId = uri?.getQueryParameter("paymentId")
        Log.i("PaymentServiceImpl", "Payment ID set from URI: $paymentId")
    }
    override suspend fun initiatePayment(paymentDetails: PaymentDetails): String {
        Log.i("PaymentServiceImpl", "initiatePayment")
        try {
            val response = apiService.initiatePayment(paymentDetails)
            if (response.isSuccessful) {
                val paymentUrl = response.body()?.paymentUrl
                paymentId = extractPaymentId(paymentUrl)
                Log.i("PaymentServiceImpl", "Payment Id: ${paymentId}")
                return response.body()?.paymentUrl
                    ?: throw Exception("Hibás válasz a szervertől: Nincs Payment URL")
            } else {
                throw Exception("Fizetési hiba: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            throw Exception("Hiba a fizetési folyamat során: ${e.message}", e)
        }
    }
    private fun extractPaymentId(paymentUrl: String?): String? {
        return paymentUrl?.substringAfter("Id=")
    }

    override suspend fun checkPaymentStatus(): String? {
        Log.d("PaymentService", "Checking payment status for payment ID: $paymentId")
        paymentId?.let {
            try {
                val response = barionApiService.getPaymentStatus(paymentId = it)
                if (response.isSuccessful) {
                    Log.d("PaymentService", "Payment status response: ${response.body()?.Status}")
                    return response.body()?.Status
                } else {
                    val errorMsg = "Payment status check failed: ${response.errorBody()?.string()}"
                    Log.e("PaymentService", errorMsg)
                    throw Exception(errorMsg)
                }
            } catch (e: Exception) {
                val errorMsg = "Error checking payment status: ${e.message}"
                Log.e("PaymentService", errorMsg)
                throw Exception(errorMsg, e)
            }
        } ?: run {
            val errorMsg = "Payment ID is missing"
            Log.e("PaymentService", errorMsg)
            throw Exception(errorMsg)
        }
    }
}
