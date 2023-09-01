package com.vishnus1224.notifyme.feature.customer.logic

import com.vishnus1224.notifyme.feature.customer.data.Customer

sealed class ListCustomerAction {
    object Init : ListCustomerAction()
    object AddCustomer : ListCustomerAction()
    data class CustomerClicked(val customer: Customer) : ListCustomerAction()
}

sealed class ListCustomerResult {
    data class CustomerList(val customers: List<Customer>) : ListCustomerResult()

    data class InProgress(val inProgress: Boolean) : ListCustomerResult()
    data class Error(val errorMessage: String) : ListCustomerResult()

    object AddNewCustomer : ListCustomerResult()

    data class ShowCustomerDetails(val customer: Customer) : ListCustomerResult()
}

data class ListCustomerState(
    val customers: List<Customer>,
    val inProgress: Boolean,
    val errorMessage: String,
)