package hu.szoftarch.webshop.model.datasource.mock

import android.net.Uri
import android.util.Log
import hu.szoftarch.webshop.model.data.PaymentDetails
import hu.szoftarch.webshop.model.service.PaymentService
import kotlinx.coroutines.delay

object PaymentServiceMock : PaymentService {

    override suspend fun initiatePayment(paymentDetails: PaymentDetails): String {
        Log.i("PaymentServiceMock", "initiatePayment")
        delay(1000)

        if (paymentDetails.cartItems.isEmpty() || paymentDetails.totalAmount <= 0) {
            throw Exception("Érvénytelen fizetési adatok")
        }

        return "https://www.index.hu"
    }

    override suspend fun checkPaymentStatus(): String? {
        TODO("Not yet implemented")
    }

    override fun setPaymentIdFromUri(uri: Uri?) {
        TODO("Not yet implemented")
    }
}
