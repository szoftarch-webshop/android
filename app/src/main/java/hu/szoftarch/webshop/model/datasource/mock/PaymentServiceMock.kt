package hu.szoftarch.webshop.model.datasource.mock

import hu.szoftarch.webshop.model.data.PaymentDetails
import hu.szoftarch.webshop.model.service.PaymentService

object PaymentServiceMock : PaymentService {
    override suspend fun initiatePayment(paymentDetails: PaymentDetails) = true
}
