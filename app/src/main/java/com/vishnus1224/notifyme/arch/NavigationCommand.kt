package com.vishnus1224.notifyme.arch

import com.vishnus1224.notifyme.feature.customer.data.Customer

interface NavigationCommand

data class GoToCustomerDetailsCommand(
    val customer: Customer
) : NavigationCommand

class GoToCustomerListCommand : NavigationCommand

class AddNewCustomerCommand : NavigationCommand