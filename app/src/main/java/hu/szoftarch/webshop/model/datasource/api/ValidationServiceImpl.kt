package hu.szoftarch.webshop.model.datasource.api

import android.util.Patterns
import hu.szoftarch.webshop.model.data.CustomerInfo
import hu.szoftarch.webshop.model.data.CustomerInfoValidationReport
import hu.szoftarch.webshop.model.service.ValidationService

object ValidationServiceImpl : ValidationService {
    override suspend fun validateCustomerInfo(customerInfo: CustomerInfo): CustomerInfoValidationReport {
        val nameError = if (customerInfo.name.isBlank()) "Name cannot be empty" else null

        val zipCodeError =
            if (!customerInfo.zipCode.matches(Regex("\\d{4}"))) {
                "Zip code must be exactly 4 digits"
            } else {
                null
            }

        val countryError = if (customerInfo.country.isBlank()) "Country cannot be empty" else null

        val cityError = if (customerInfo.city.isBlank()) "City cannot be empty" else null

        val streetError = if (customerInfo.street.isBlank()) "Street cannot be empty" else null

        val phoneNumberError =
            if (!Patterns.PHONE.matcher(customerInfo.phoneNumber).matches()) {
                "Invalid phone number"
            } else {
                null
            }

        val emailAddressError =
            if (!Patterns.EMAIL_ADDRESS.matcher(customerInfo.emailAddress).matches()) {
                "Invalid email address"
            } else {
                null
            }

        return CustomerInfoValidationReport(
            nameError = nameError,
            zipCodeError = zipCodeError,
            countryError = countryError,
            cityError = cityError,
            streetError = streetError,
            phoneNumberError = phoneNumberError,
            emailAddressError = emailAddressError
        )
    }
}
