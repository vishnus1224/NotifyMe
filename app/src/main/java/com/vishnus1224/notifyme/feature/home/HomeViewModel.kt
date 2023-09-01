package com.vishnus1224.notifyme.feature.home

import com.vishnus1224.notifyme.arch.BaseViewModel
import com.vishnus1224.notifyme.arch.NavigationCommand
import com.vishnus1224.notifyme.arch.Router
import com.vishnus1224.notifyme.feature.home.HomeResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import com.vishnus1224.notifyme.feature.home.HomeAction as Action
import com.vishnus1224.notifyme.feature.home.HomeResult as Result
import com.vishnus1224.notifyme.feature.home.HomeState as State

@HiltViewModel
class HomeViewModel
@Inject constructor(
    interactor: HomeInteractor,
    dispatcher: CoroutineDispatcher,
    router: HomeRouter,
) : BaseViewModel<Action, Result, State>(interactor, dispatcher, router) {

    fun onNavigate(command: NavigationCommand) = sendAction(
        Action.Navigate(command)
    )

    override fun initialState(): State {
        return State.CustomerList
    }

    override fun reduce(result: Result, state: State): State {
        return when (result) {
            is Result.ShowCustomerDetails -> State.CustomerDetails(result.customer)
            is Result.ShowCustomerList -> State.CustomerList
            is HomeResult.AddCustomer -> State.AddCustomer
        }
    }
}