package com.vishnus1224.notifyme.feature.customer.logic

import com.vishnus1224.notifyme.arch.Interactor
import com.vishnus1224.notifyme.feature.customer.data.CustomerRepository
import javax.inject.Inject
import com.vishnus1224.notifyme.feature.customer.logic.AddCustomerAction as Action
import com.vishnus1224.notifyme.feature.customer.logic.AddCustomerResult as Result

class AddCustomerInteractor
@Inject constructor(
    private val customerRepository: CustomerRepository,
) : Interactor<Action, Result>() {

    override suspend fun process(action: Action) {
        when (action) {
            is Action.NameChanged -> customerNameUpdated(action.name)
            is Action.AddressChanged -> customerAddressUpdated(action.address)
            is Action.PhoneNumberChanged -> customerPhoneUpdated(action.phoneNumber)
            is Action.SaveCustomer -> saveCustomer(action)
        }
    }

    private suspend fun saveCustomer(action: Action.SaveCustomer) {
        sendResults(Result.InProgress(true))

        customerRepository.saveCustomer(action.customer)

        sendResults(
            Result.InProgress(false),
            Result.CustomerSaved,
        )
    }

    private suspend fun customerNameUpdated(name: String) {
        sendResults(Result.UpdateName(name))
    }

    private suspend fun customerAddressUpdated(address: String) {
        sendResults(Result.UpdateAddress(address))
    }

    private suspend fun customerPhoneUpdated(phoneNumber: String) {
        sendResults(Result.UpdatePhoneNumber(phoneNumber))
    }

    override fun destroy() {
        customerRepository.destroy()
    }
}