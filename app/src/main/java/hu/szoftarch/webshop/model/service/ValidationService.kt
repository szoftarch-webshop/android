package hu.szoftarch.webshop.model.service

import hu.szoftarch.webshop.model.data.CustomerInfo
import hu.szoftarch.webshop.model.data.CustomerInfoValidationReport

interface ValidationService {
    suspend fun validateCustomerInfo(customerInfo: CustomerInfo): CustomerInfoValidationReport
}
