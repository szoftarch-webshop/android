package hu.szoftarch.webshop.model.service

import hu.szoftarch.webshop.model.data.PaymentDetails

interface PaymentService {
    suspend fun initiatePayment(paymentDetails: PaymentDetails): Boolean
}
