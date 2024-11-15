package hu.szoftarch.webshop.model.datasource.impl

import android.util.Log
import hu.szoftarch.webshop.model.api.ApiService
import hu.szoftarch.webshop.model.data.PaymentDetails
import hu.szoftarch.webshop.model.service.PaymentService
import javax.inject.Inject

class PaymentServiceImpl @Inject constructor(
    private val apiService: ApiService
) : PaymentService
{
    override suspend fun initiatePayment(paymentDetails: PaymentDetails): String {
        Log.i("PaymentServiceImpl", "initiatePayment")
        try {
            val response = apiService.initiatePayment(paymentDetails)
            if (response.isSuccessful) {
                return response.paymentUrl
                    ?: throw Exception("Hibás válasz a szervertől: Nincs Payment URL")
            } else {
                throw Exception("Fizetési hiba: ${response.errorBody}")
            }
        } catch (e: Exception) {
            throw Exception("Hiba a fizetési folyamat során: ${e.message}", e)
        }
    }
}