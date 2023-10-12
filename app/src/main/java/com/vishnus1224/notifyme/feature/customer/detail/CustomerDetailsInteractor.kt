package com.vishnus1224.notifyme.feature.customer.detail

import com.vishnus1224.notifyme.arch.Interactor
import com.vishnus1224.notifyme.feature.customer.data.Customer
import com.vishnus1224.notifyme.feature.customer.data.CustomerRepository
import java.util.UUID
import javax.inject.Inject
import com.vishnus1224.notifyme.feature.customer.detail.CustomerDetailsAction as Action
import com.vishnus1224.notifyme.feature.customer.detail.CustomerDetailsResult as Result

class CustomerDetailsInteractor
@Inject constructor(
  private val customerRepository: CustomerRepository,
) : Interactor<Action, Result>() {

  override suspend fun process(action: Action) {
    when (action) {
      is Action.Init -> getCustomerDetails(action.customerId)
      is Action.NameChanged -> customerNameUpdated(action.name)
      is Action.AddressChanged -> customerAddressUpdated(action.address)
      is Action.PhoneNumberChanged -> customerPhoneUpdated(action.phoneNumber)
      is Action.SaveCustomer -> saveCustomer(action)
    }
  }

  private suspend fun getCustomerDetails(customerId: String?) {
    sendResults(Result.InProgress(true))

    val existingCustomer = customerRepository.getCustomer(customerId)

    sendResults(
      Result.InProgress(false),
    )

    if (existingCustomer != null) {
      sendResults(
        Result.ShowCustomerDetails(existingCustomer)
      )
    }
  }

  private suspend fun saveCustomer(action: Action.SaveCustomer) {
    sendResults(Result.InProgress(true))

    val existingCustomer = customerRepository.getCustomer(action.customerId)

    val customerToSave = Customer(
      id = action.customerId ?: UUID.randomUUID().toString(),
      phoneNumber = action.phoneNumber,
      name = action.name,
      address = action.address,
      createdAt = existingCustomer?.createdAt ?: System.currentTimeMillis(),
    )

    customerRepository.saveCustomer(
      customer = customerToSave,
    )

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