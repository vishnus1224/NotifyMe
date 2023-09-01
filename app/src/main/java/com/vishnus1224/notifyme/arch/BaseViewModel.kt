package com.vishnus1224.notifyme.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<ACTION, RESULT, STATE>(
    private val interactor: Interactor<ACTION, RESULT>,
    private val dispatcher: CoroutineDispatcher,
    private val router: Router,
) : ViewModel() {

    private val state by lazy {
        MutableStateFlow(initialState())
    }

    init {
        viewModelScope.launch(dispatcher) {
            interactor.results()
                .collect {
                    state.value = reduce(it, state.value)
                }
        }
    }

    fun state(): StateFlow<STATE> = state.asStateFlow()
    fun navigation(): Flow<NavigationCommand> = router.navigationCommands()

    protected fun sendAction(action: ACTION) = viewModelScope.launch(dispatcher) {
        interactor.process(action)
    }

    protected abstract fun initialState(): STATE
    protected abstract fun reduce(result: RESULT, state: STATE): STATE

    override fun onCleared() {
        interactor.destroy()
        router.destroy()
        super.onCleared()
    }
}