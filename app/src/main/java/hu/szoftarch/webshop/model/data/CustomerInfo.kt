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

data class CustomerInfoValidationReport(
    val nameError: String? = null,
    val zipCodeError: String? = null,
    val countryError: String? = null,
    val cityError: String? = null,
    val streetError: String? = null,
    val phoneNumberError: String? = null,
    val emailAddressError: String? = null
) {
    val hasErrors
        get() = listOf(
            nameError, zipCodeError, countryError, cityError,
            streetError, phoneNumberError, emailAddressError
        ).any { it != null }

    fun isValid() = !hasErrors
}
