package hu.szoftarch.webshop.model.data

data class PaymentResponse(
    val isSuccessful : Boolean,
    val paymentUrl: String? ,
    val errorBody: String?
)