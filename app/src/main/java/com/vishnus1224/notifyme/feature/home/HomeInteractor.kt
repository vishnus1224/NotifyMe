package com.vishnus1224.notifyme.feature.home

import com.vishnus1224.notifyme.arch.AddNewCustomerCommand
import com.vishnus1224.notifyme.arch.GoToCustomerDetailsCommand
import com.vishnus1224.notifyme.arch.GoToCustomerListCommand
import com.vishnus1224.notifyme.arch.Interactor
import com.vishnus1224.notifyme.arch.NavigationCommand
import com.vishnus1224.notifyme.feature.customer.data.Customer
import javax.inject.Inject
import com.vishnus1224.notifyme.feature.home.HomeAction as Action
import com.vishnus1224.notifyme.feature.home.HomeResult as Result

class HomeInteractor
@Inject constructor(

) : Interactor<Action, Result>() {

    override suspend fun process(action: Action) {
        when (action) {
            is Action.Navigate -> performNavigation(action.command)
        }
    }

    private suspend fun performNavigation(command: NavigationCommand) {
        when (command) {
           is GoToCustomerListCommand -> showCustomerListScreen()
           is GoToCustomerDetailsCommand -> showCustomerDetails(command.customer)
           is AddNewCustomerCommand -> showCustomerDetails(Customer())
        }
    }

    private suspend fun showCustomerListScreen() {
        sendResults(Result.ShowCustomerList)
    }

    private suspend fun showCustomerDetails(customer: Customer) {
        sendResults(Result.ShowCustomerDetails(customer))
    }

    override fun destroy() {

    }
}