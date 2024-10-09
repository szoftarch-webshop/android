package hu.szoftarch.webshop.feature.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.szoftarch.webshop.model.data.CustomerInfo
import hu.szoftarch.webshop.model.service.ValidationService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerInfoViewModel @Inject constructor(private val validationService: ValidationService) :
    ViewModel() {
    data class UIState(
        val name: String = "",
        val zipCode: String = "",
        val country: String = "",
        val city: String = "",
        val street: String = "",
        val phoneNumber: String = "",
        val emailAddress: String = "",
        val nameError: String? = null,
        val zipCodeError: String? = null,
        val countryError: String? = null,
        val cityError: String? = null,
        val streetError: String? = null,
        val phoneNumberError: String? = null,
        val emailAddressError: String? = null
    )

    var uiState by mutableStateOf(UIState())

    fun onNameChanged(name: String) {
        uiState = uiState.copy(name = name)
    }

    fun onZipCodeChanged(zipCode: String) {
        uiState = uiState.copy(zipCode = zipCode)
    }

    fun onCountryChanged(country: String) {
        uiState = uiState.copy(country = country)
    }

    fun onCityChanged(city: String) {
        uiState = uiState.copy(city = city)
    }

    fun onStreetChanged(street: String) {
        uiState = uiState.copy(street = street)
    }

    fun onPhoneNumberChanged(phoneNumber: String) {
        uiState = uiState.copy(phoneNumber = phoneNumber)
    }

    fun onEmailAddressChanged(emailAddress: String) {
        uiState = uiState.copy(emailAddress = emailAddress)
    }

    fun validate() = viewModelScope.launch {
        val report = validationService.validateCustomerInfo(customerInfo())
        uiState = uiState.copy(
            nameError = report.nameError,
            zipCodeError = report.zipCodeError,
            countryError = report.countryError,
            cityError = report.cityError,
            streetError = report.streetError,
            phoneNumberError = report.phoneNumberError,
            emailAddressError = report.emailAddressError
        )
    }

    private fun customerInfo() = CustomerInfo(
        name = uiState.name,
        zipCode = uiState.zipCode,
        country = uiState.country,
        city = uiState.city,
        street = uiState.street,
        phoneNumber = uiState.phoneNumber,
        emailAddress = uiState.emailAddress
    )
}
