package com.vishnus1224.notifyme.feature.customer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vishnus1224.notifyme.R
import com.vishnus1224.notifyme.arch.NavigationCommand
import com.vishnus1224.notifyme.feature.customer.logic.AddCustomerState
import com.vishnus1224.notifyme.feature.customer.logic.AddCustomerViewModel

@Composable
fun AddCustomerScreen(
    viewModel: AddCustomerViewModel,
    navigator: (NavigationCommand) -> Unit,
    modifier: Modifier = Modifier,
) {

    val state = viewModel.state().collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel) {
        viewModel.navigation().collect(navigator)
    }

    AddCustomerScreen(
        state = state.value,
        onNameChanged = viewModel::onNameChanged,
        onAddressChanged = viewModel::onAddressChanged,
        onPhoneNumberChanged = viewModel::onPhoneNumberChanged,
        onSaveClicked = viewModel::onSaveCustomerClicked,
        modifier = modifier,
    )
}

@Composable
private fun AddCustomerScreen(
    state: AddCustomerState,
    onNameChanged: (String) -> Unit,
    onAddressChanged: (String) -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    onSaveClicked: (name: String, address: String, phoneNumber: String) -> Unit,
    modifier: Modifier,
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        CenterAlignedTopAppBar(
            title = {
                Text(text = stringResource(id = R.string.enter_customer_details))
            }
        )

        TextField(
            value = state.name,
            onValueChange = { onNameChanged(it) },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(text = stringResource(id = R.string.enter_name))
            },
        )

        TextField(
            value = state.address,
            onValueChange = { onAddressChanged(it) },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(text = stringResource(id = R.string.enter_address))
            },
        )

        TextField(
            value = state.phoneNumber,
            onValueChange = { onPhoneNumberChanged(it) },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(text = stringResource(id = R.string.enter_phone_number))
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
            ),
        )

        Button(onClick = { onSaveClicked(state.name, state.address, state.phoneNumber) }) {
            Text(text = "save")
        }
    }
}

@Preview
@Composable
private fun AddCustomerScreenPreview() {
    AddCustomerScreen(
        state = AddCustomerState(
            name = "abc",
            address = "122 Downling Street",
            phoneNumber = "+44894928884",
            inProgress = false,
            errorMessage = "",
        ),
        onNameChanged = { },
        onAddressChanged = {},
        onPhoneNumberChanged = { },
        onSaveClicked = { _, _, _ -> },
        modifier = Modifier,
    )
}