package com.vishnus1224.notifyme.feature.customer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vishnus1224.notifyme.arch.NavigationCommand
import com.vishnus1224.notifyme.feature.customer.data.Customer
import com.vishnus1224.notifyme.feature.customer.logic.ListCustomerState
import com.vishnus1224.notifyme.feature.customer.logic.ListCustomerViewModel

@Composable
fun ListCustomerScreen(
    viewModel: ListCustomerViewModel,
    navigator: (NavigationCommand) -> Unit,
    modifier: Modifier = Modifier,
) {

    val state = viewModel.state().collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel) {
        viewModel.navigation().collect(navigator)
    }

    ListCustomerScreen(
        state = state.value,
        onCustomerClick = viewModel::onCustomerClick,
        onAddClick = viewModel::onAddCustomerClick,
        modifier = modifier,
    )

}

@Composable
private fun ListCustomerScreen(
    state: ListCustomerState,
    onCustomerClick: (Customer) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier,
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CenterAlignedTopAppBar(
            title = { Text(text = "List of customers") },
        )

        Button(onClick = onAddClick) {
            Text(text = "Add new")
        }

        if (state.customers.isNotEmpty()) {
            LazyColumn {
                items(state.customers) {
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCustomerClick(it) }
                            .padding(16.dp)
                    )
                }
            }
        } else if (state.errorMessage.isNotBlank()) {
            Text(text = state.errorMessage)
        } else if (state.inProgress) {
            CircularProgressIndicator()
        } else {
            Text(text = "No customers found")
        }
    }
}

@Preview
@Composable
private fun ListCustomerScreenPreview() {
    ListCustomerScreen(
        state = ListCustomerState(
            customers = listOf(
                Customer("abc", "122, Downing Street", "+4432839992", 0L)
            ),
            inProgress = false,
            errorMessage = "",
        ),
        onCustomerClick = { },
        onAddClick = { },
        modifier = Modifier,
    )
}