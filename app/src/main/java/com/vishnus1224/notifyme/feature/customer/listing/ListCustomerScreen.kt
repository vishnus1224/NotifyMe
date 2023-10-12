package com.vishnus1224.notifyme.feature.customer.listing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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

@Composable
fun ListCustomerScreen(
  viewModel: ListCustomerViewModel,
  navigator: (NavigationCommand) -> Unit,
  modifier: Modifier = Modifier,
) {
  val state = viewModel.state().collectAsStateWithLifecycle()

  LaunchedEffect(key1 = viewModel) {

    viewModel.getAllCustomers()

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
  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        title = { Text(text = "List of customers") },
      )
    },
    floatingActionButton = {
      FloatingActionButton(onClick = onAddClick) {
        Icon(Icons.Default.Add, contentDescription = "Add new customer")
      }
    },
    modifier = modifier,
  ) { paddingValues ->

    Column(
      modifier = modifier.padding(paddingValues),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {

      if (state.customers.isNotEmpty()) {
        LazyColumn {
          itemsIndexed(state.customers) { index, customer ->
            Text(
              text = customer.name,
              modifier = Modifier
                  .fillMaxWidth()
                  .clickable { onCustomerClick(customer) }
                  .padding(16.dp)
            )

            // Add a divider if not the last item
            if (index != state.customers.size - 1) {
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
            }
          }
        }
      } else if (state.inProgress.not()) {
        Text(text = "No customers found")
      }

      if (state.errorMessage.isNotBlank()) {
        Text(text = state.errorMessage)
      }

      if (state.inProgress) {
        CircularProgressIndicator()
      }
    }
  }
}

@Preview
@Composable
private fun ListCustomerScreenPreview() {
  ListCustomerScreen(
    state = ListCustomerState(
      customers = listOf(
        Customer("", "abc", "122, Downing Street", "+4432839992", 0L)
      ),
      inProgress = false,
      errorMessage = "",
    ),
    onCustomerClick = { },
    onAddClick = { },
    modifier = Modifier,
  )
}