package com.vishnus1224.notifyme.feature.customer.logic

import com.vishnus1224.notifyme.arch.BaseViewModel
import com.vishnus1224.notifyme.feature.customer.data.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import com.vishnus1224.notifyme.feature.customer.logic.AddCustomerAction as Action
import com.vishnus1224.notifyme.feature.customer.logic.AddCustomerResult as Result
import com.vishnus1224.notifyme.feature.customer.logic.AddCustomerState as State

@HiltViewModel
class AddCustomerViewModel
@Inject constructor(
    interactor: AddCustomerInteractor,
    dispatcher: CoroutineDispatcher,
    private val router: AddCustomerRouter
) : BaseViewModel<Action, Result, State>(interactor, dispatcher, router) {

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
        }
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
            customer = Customer(phoneNumber, name, address, System.currentTimeMillis())
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