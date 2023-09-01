package com.vishnus1224.notifyme.feature.customer.logic

import com.vishnus1224.notifyme.arch.AddNewCustomerCommand
import com.vishnus1224.notifyme.arch.GoToCustomerDetailsCommand
import com.vishnus1224.notifyme.arch.Router
import com.vishnus1224.notifyme.feature.customer.data.Customer
import javax.inject.Inject

class ListCustomerRouter
@Inject constructor(

) : Router() {

    fun goToCustomerDetails(customer: Customer) = sendCommand(
        GoToCustomerDetailsCommand(customer)
    )

    fun goToAddNewCustomer() = sendCommand(
        AddNewCustomerCommand()
    )
}