package com.vishnus1224.notifyme.feature.customer.logic

import com.vishnus1224.notifyme.feature.customer.data.Customer

sealed class AddCustomerAction {

    data class NameChanged(val name: String) : AddCustomerAction()

    data class AddressChanged(val address: String) : AddCustomerAction()

    data class PhoneNumberChanged(val phoneNumber: String) : AddCustomerAction()

    data class SaveCustomer(
        val customer: Customer,
    ) : AddCustomerAction()
}

sealed class AddCustomerResult {
    data class UpdateName(val name: String) : AddCustomerResult()
    data class UpdateAddress(val address: String) : AddCustomerResult()
    data class UpdatePhoneNumber(val phoneNumber: String) : AddCustomerResult()

    object CustomerSaved : AddCustomerResult()
    data class InProgress(val inProgress: Boolean) : AddCustomerResult()

    data class ErrorWhileSavingCustomer(val message: String) : AddCustomerResult()
}

data class AddCustomerState(
    val name: String,
    val address: String,
    val phoneNumber: String,
    val inProgress: Boolean,
    val errorMessage: String,
)