package com.vishnus1224.notifyme.feature.customer.logic

import com.vishnus1224.notifyme.arch.Interactor
import com.vishnus1224.notifyme.feature.customer.data.Customer
import com.vishnus1224.notifyme.feature.customer.data.CustomerRepository
import java.lang.Exception
import java.util.concurrent.CancellationException
import javax.inject.Inject
import com.vishnus1224.notifyme.feature.customer.logic.ListCustomerAction as Action
import com.vishnus1224.notifyme.feature.customer.logic.ListCustomerResult as Result

class ListCustomerInteractor
@Inject constructor(
    private val customerRepository: CustomerRepository,
) : Interactor<Action, Result>() {

    override suspend fun process(action: Action) {
        when (action) {
            is Action.Init -> getAllCustomers()
            is Action.AddCustomer -> addNewCustomer()
            is Action.CustomerClicked -> customerClicked(action.customer)
        }
    }

    private suspend fun getAllCustomers() {
        sendResults(Result.InProgress(true))

        try {
            val customers: List<Customer> = customerRepository.getAllCustomers()

            sendResults(
                Result.InProgress(false),
                Result.CustomerList(customers),
            )

        } catch (e: Exception) {
            if (e !is CancellationException) {
                sendResults(
                    Result.InProgress(false),
                    Result.Error("Failed to get customers ${e.message}")
                )
            }
        }
    }

    private suspend fun customerClicked(customer: Customer) {
        sendResults(
            Result.ShowCustomerDetails(customer),
        )
    }

    private suspend fun addNewCustomer() {
        sendResults(Result.AddNewCustomer)
    }

    override fun destroy() {
        customerRepository.destroy()
    }
}