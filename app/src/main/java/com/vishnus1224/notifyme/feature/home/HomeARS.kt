package com.vishnus1224.notifyme.feature.home

import com.vishnus1224.notifyme.arch.NavigationCommand
import com.vishnus1224.notifyme.feature.customer.data.Customer

sealed class HomeAction {
    data class Navigate(val command: NavigationCommand) : HomeAction()
}

sealed class HomeResult {
    object ShowCustomerList : HomeResult()
    object AddCustomer : HomeResult()
    data class ShowCustomerDetails(val customer: Customer) : HomeResult()
}

sealed class HomeState {
    object CustomerList : HomeState()
    object AddCustomer : HomeState()
    data class CustomerDetails(val customer: Customer) : HomeState()
}