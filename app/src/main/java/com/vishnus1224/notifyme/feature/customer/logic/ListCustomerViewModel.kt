package com.vishnus1224.notifyme.feature.customer.logic

import com.vishnus1224.notifyme.arch.BaseViewModel
import com.vishnus1224.notifyme.feature.customer.data.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import com.vishnus1224.notifyme.feature.customer.logic.ListCustomerAction as Action
import com.vishnus1224.notifyme.feature.customer.logic.ListCustomerResult as Result
import com.vishnus1224.notifyme.feature.customer.logic.ListCustomerState as State

@HiltViewModel
class ListCustomerViewModel
@Inject constructor(
    interactor: ListCustomerInteractor,
    dispatcher: CoroutineDispatcher,
    private val router: ListCustomerRouter,
) : BaseViewModel<Action, Result, State>(interactor, dispatcher, router) {

    init {
        sendAction(Action.Init)
    }

    fun onCustomerClick(customer: Customer) = sendAction(
        Action.CustomerClicked(customer)
    )

    fun onAddCustomerClick() = sendAction(
        Action.AddCustomer
    )

    override fun initialState(): State {
        return State(
            customers = emptyList(),
            inProgress = false,
            errorMessage = "",
        )
    }

    override fun reduce(result: Result, state: State): State {
        return when (result) {
            is Result.CustomerList -> showCustomerList(result.customers, state)
            is Result.Error -> state.copy(errorMessage = result.errorMessage)
            is Result.InProgress -> state.copy(inProgress = result.inProgress)
            is Result.ShowCustomerDetails -> {
                router.goToCustomerDetails(result.customer)
                state
            }
            is Result.AddNewCustomer -> {
                router.goToAddNewCustomer()
                state
            }
        }
    }

    private fun showCustomerList(customers: List<Customer>, state: State): State {
        return state.copy(
            customers = customers,
        )
    }
}