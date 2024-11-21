package hu.szoftarch.webshop.model.service

import android.net.Uri
import hu.szoftarch.webshop.model.data.PaymentDetails

interface PaymentService {
    suspend fun initiatePayment(paymentDetails: PaymentDetails): String
    suspend fun checkPaymentStatus(): String?
    fun setPaymentIdFromUri(uri: Uri?)
}