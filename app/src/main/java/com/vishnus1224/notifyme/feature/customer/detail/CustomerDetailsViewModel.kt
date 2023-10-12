package com.vishnus1224.notifyme.feature.customer.detail

import androidx.lifecycle.SavedStateHandle
import com.vishnus1224.notifyme.arch.BaseViewModel
import com.vishnus1224.notifyme.feature.customer.data.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import com.vishnus1224.notifyme.feature.customer.detail.CustomerDetailsAction as Action
import com.vishnus1224.notifyme.feature.customer.detail.CustomerDetailsResult as Result
import com.vishnus1224.notifyme.feature.customer.detail.CustomerDetailsState as State

@HiltViewModel
class CustomerDetailsViewModel
@Inject constructor(
    interactor: CustomerDetailsInteractor,
    dispatcher: CoroutineDispatcher,
    private val router: CustomerDetailsRouter,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<Action, Result, State>(interactor, dispatcher, router) {

    private val customerId: String? = savedStateHandle["customerId"]

    init {
      sendAction(
          Action.Init(customerId)
      )
    }

    override fun initialState(): State = State(
        name = "",
        address = "",
        phoneNumber = "",
        inProgress = false,
        errorMessage = "",
    )

    override fun reduce(result: Result, state: State): State {
        return when (result) {
            is Result.CustomerSaved -> customerSaved(state)
            is Result.UpdateName -> updateCustomerName(result.name, state)
            is Result.UpdateAddress -> updateCustomerAddress(result.address, state)
            is Result.UpdatePhoneNumber -> updateCustomerPhoneNumber(result.phoneNumber, state)
            is Result.ErrorWhileSavingCustomer -> handleError(result, state)
            is Result.InProgress -> state.copy(inProgress = result.inProgress)
            is Result.ShowCustomerDetails -> showCustomerDetails(result.customer, state)
        }
    }

    private fun showCustomerDetails(customer: Customer, state: State): State {
        return state.copy(
            name = customer.name,
            address = customer.address,
            phoneNumber = customer.phoneNumber,
        )
    }

    private fun updateCustomerAddress(address: String, state: State): State {
        return state.copy(
            address = address,
        )
    }

    private fun updateCustomerPhoneNumber(phoneNumber: String, state: State): State {
        return state.copy(
            phoneNumber = phoneNumber,
        )
    }

    private fun updateCustomerName(name: String, state: State): State {
        return state.copy(
            name = name,
        )
    }

    fun onNameChanged(name: String) = sendAction(Action.NameChanged(name))

    fun onAddressChanged(address: String) = sendAction(Action.AddressChanged(address))

    fun onPhoneNumberChanged(phoneNumber: String) = sendAction(Action.PhoneNumberChanged(phoneNumber))

    fun onSaveCustomerClicked(name: String, address: String, phoneNumber: String) = sendAction(
        Action.SaveCustomer(
            customerId = customerId,
            phoneNumber = phoneNumber,
            name = name,
            address = address,
        )
    )

    private fun handleError(result: Result.ErrorWhileSavingCustomer, state: State): State {
        return state.copy(errorMessage = result.message)
    }

    private fun customerSaved(state: State): State {
        router.goToCustomerList()
        return state
    }
}