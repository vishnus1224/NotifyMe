package com.vishnus1224.notifyme.feature.customer.logic

import com.vishnus1224.notifyme.arch.GoToCustomerListCommand
import com.vishnus1224.notifyme.arch.Router
import javax.inject.Inject

class AddCustomerRouter
@Inject constructor(

) : Router() {

    fun goToCustomerList() = sendCommand(
        GoToCustomerListCommand()
    )
}