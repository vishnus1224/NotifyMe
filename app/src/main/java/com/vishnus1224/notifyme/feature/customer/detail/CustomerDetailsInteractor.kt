package com.vishnus1224.notifyme.feature.customer.detail

import com.vishnus1224.notifyme.arch.Interactor
import com.vishnus1224.notifyme.feature.customer.data.Customer
import com.vishnus1224.notifyme.feature.customer.data.CustomerRepository
import com.vishnus1224.notifyme.feature.customer.detail.CustomerDetailsAction
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
      is CustomerDetailsAction.ReminderDateSelected -> reminderDateUpdated(action.dateMillis)
      is CustomerDetailsAction.DismissDatePicker -> dismissDatePicker()
      is CustomerDetailsAction.SelectDate -> showDatePicker()
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
      reminderDate = action.reminderDate,
    )

    customerRepository.saveCustomer(
      customer = customerToSave,
    )

    setReminder(action.reminderDate)

    sendResults(
      Result.InProgress(false),
      Result.CustomerSaved,
    )
  }

  private suspend fun setReminder(reminderDate: Long) {

  }

  private suspend fun reminderDateUpdated(dateMillis: Long?) {
    sendResults(Result.UpdateReminderDate(dateMillis))
  }

  private suspend fun dismissDatePicker() {
    sendResults(Result.DismissDatePicker)
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

  private suspend fun showDatePicker() {
    sendResults(Result.ShowDatePicker)
  }

  override fun destroy() {
    customerRepository.destroy()
  }
}