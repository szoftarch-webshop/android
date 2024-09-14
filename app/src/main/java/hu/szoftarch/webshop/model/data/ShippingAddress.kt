package hu.szoftarch.webshop.model.data

data class ShippingAddress(
    val uniqueID: String,
    val name: String,
    val email: String,
    val zipCode: String,
    val country: String,
    val city: String,
    val street: String,
    val phoneNumber: String
)