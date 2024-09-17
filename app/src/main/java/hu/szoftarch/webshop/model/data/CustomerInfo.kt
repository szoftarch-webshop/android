package hu.szoftarch.webshop.model.data

data class CustomerInfo(
    val name: String,
    val zipCode: String,
    val country: String,
    val city: String,
    val street: String,
    val phoneNumber: String,
    val emailAddress: String
)
