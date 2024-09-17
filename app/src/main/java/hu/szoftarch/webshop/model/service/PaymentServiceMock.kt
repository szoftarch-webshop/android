package hu.szoftarch.webshop.model.service

import hu.szoftarch.webshop.model.data.PaymentDetails

object PaymentServiceMock : PaymentService {
    override suspend fun initiatePayment(paymentDetails: PaymentDetails) = true
}
