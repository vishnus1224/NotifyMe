package com.vishnus1224.notifyme.feature.customer.detail

import com.vishnus1224.notifyme.feature.customer.data.Customer

sealed class CustomerDetailsAction {

    data class Init(val customerId: String?) : CustomerDetailsAction()

    data class NameChanged(val name: String) : CustomerDetailsAction()

    data class AddressChanged(val address: String) : CustomerDetailsAction()

    data class PhoneNumberChanged(val phoneNumber: String) : CustomerDetailsAction()

    data class ReminderDateSelected(val dateMillis: Long?) : CustomerDetailsAction()

    data object DismissDatePicker : CustomerDetailsAction()

    data class SaveCustomer(
        val customerId: String?,
        val name: String,
        val address: String,
        val phoneNumber: String,
        val reminderDate: Long,
    ) : CustomerDetailsAction()

    data object SelectDate : CustomerDetailsAction()
}

sealed class CustomerDetailsResult {
    data class UpdateName(val name: String) : CustomerDetailsResult()
    data class UpdateAddress(val address: String) : CustomerDetailsResult()
    data class UpdatePhoneNumber(val phoneNumber: String) : CustomerDetailsResult()

    data object CustomerSaved : CustomerDetailsResult()
    data class InProgress(val inProgress: Boolean) : CustomerDetailsResult()

    data class ErrorWhileSavingCustomer(val message: String) : CustomerDetailsResult()

    data class ShowCustomerDetails(val customer: Customer) : CustomerDetailsResult()

    data class UpdateReminderDate(val dateMillis: Long?) : CustomerDetailsResult()
    data object ShowDatePicker : CustomerDetailsResult()

    data object DismissDatePicker : CustomerDetailsResult()
}

data class CustomerDetailsState(
    val name: String,
    val address: String,
    val phoneNumber: String,
    val inProgress: Boolean,
    val errorMessage: String,
    val showDatePicker: Boolean,
    val reminderDate: Long?
)