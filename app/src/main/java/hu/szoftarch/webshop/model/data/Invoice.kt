package hu.szoftarch.webshop.model.data

data class Invoice(
    val uniqueID: String,
    val customerName: String,
    val customerZipCode: String,
    val customerCountry: String,
    val customerCity: String,
    val customerStreet: String,
    val customerPhoneNumber: String,
    val customerEmail: String,
    val creationDate: String,
    val orderId: String,
    val paymentMethodId: String
)

