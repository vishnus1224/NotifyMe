package com.vishnus1224.notifyme.feature.customer.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vishnus1224.notifyme.R
import com.vishnus1224.notifyme.arch.NavigationCommand
import java.util.Locale

@Composable
fun CustomerDetailsScreen(
  viewModel: CustomerDetailsViewModel,
  navigator: (NavigationCommand) -> Unit,
  modifier: Modifier = Modifier,
) {

  val state = viewModel.state().collectAsStateWithLifecycle()

  LaunchedEffect(key1 = viewModel) {
    viewModel.navigation().collect(navigator)
  }

  CustomerDetailsScreen(
    state = state.value,
    onNameChanged = viewModel::onNameChanged,
    onAddressChanged = viewModel::onAddressChanged,
    onPhoneNumberChanged = viewModel::onPhoneNumberChanged,
    onDateSelected = viewModel::onReminderDateSelected,
    onDatePickerDismissed = viewModel::onDatePickerDismissed,
    onSelectDateClicked = viewModel::onSelectDateClicked,
    onSaveClicked = viewModel::onSaveCustomerClicked,
    formatDate = viewModel::formatReminderDate,
    enableSaveButton = viewModel::enableSaveButton,
    modifier = modifier,
  )
}

@Composable
private fun CustomerDetailsScreen(
  state: CustomerDetailsState,
  onNameChanged: (String) -> Unit,
  onAddressChanged: (String) -> Unit,
  onPhoneNumberChanged: (String) -> Unit,
  onDateSelected: (Long?) -> Unit,
  onDatePickerDismissed: () -> Unit,
  onSelectDateClicked: () -> Unit,
  onSaveClicked: (CustomerDetailsState) -> Unit,
  formatDate: (Long) -> String,
  enableSaveButton: (CustomerDetailsState) -> Boolean,
  modifier: Modifier,
) {

  Column(
    modifier = modifier
      .fillMaxHeight()
      .verticalScroll(
        state = rememberScrollState(),
      ),
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
      modifier = Modifier.fillMaxWidth(),
      label = {
        Text(text = stringResource(id = R.string.enter_name))
      },
      keyboardOptions = KeyboardOptions.Default.copy(
        capitalization = KeyboardCapitalization.Words,
      )
    )

    TextField(
      value = state.address,
      onValueChange = { onAddressChanged(it) },
      modifier = Modifier.fillMaxWidth(),
      label = {
        Text(text = stringResource(id = R.string.enter_address))
      },
      keyboardOptions = KeyboardOptions.Default.copy(
        capitalization = KeyboardCapitalization.Sentences,
      )
    )

    TextField(
      value = state.phoneNumber,
      onValueChange = { onPhoneNumberChanged(it) },
      modifier = Modifier.fillMaxWidth(),
      label = {
        Text(text = stringResource(id = R.string.enter_phone_number))
      },
      keyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Phone,
      ),
    )

    val datePickerState = DatePickerState(
      initialSelectedDateMillis = state.reminderDate,
      selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
          // Allow only future dates.
          return utcTimeMillis > System.currentTimeMillis()
        }
      },
      locale = Locale.getDefault(),
    )

    if (state.showDatePicker) {
      DatePickerDialog(
        onDismissRequest = onDatePickerDismissed,
        confirmButton = {
          Button(onClick = { onDateSelected(datePickerState.selectedDateMillis) }) {
            Text(text = "Select")
          }
        }
      ) {
        DatePicker(
          state = datePickerState,
          title = {
            Text(
              text = "Select Reminder date",
              fontSize = 20.sp,
              modifier = Modifier.padding(all = 8.dp),
            )
          },
        )
      }
    } else {

      val buttonText: String = if (state.reminderDate != null) {
        "Reminder Date: \n${formatDate(state.reminderDate)}"
      } else {
        "Select Reminder date"
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.clickable { onSelectDateClicked() },
      ) {
        Text(
          text = buttonText,
          modifier = Modifier.weight(1f),
        )
        Icon(
          painter = painterResource(id = android.R.drawable.ic_menu_edit),
          contentDescription = "Edit reminder date",
        )
      }
    }

    Spacer(modifier = Modifier.weight(1f))

    Button(
      onClick = { onSaveClicked(state) },
      enabled = enableSaveButton(state),
      modifier = Modifier.fillMaxWidth(),
    ) {
      Text(text = "Save")
    }
  }
}

@Preview
@Composable
private fun CustomerDetailsScreenPreview() {
  CustomerDetailsScreen(
    state = CustomerDetailsState(
      name = "abc",
      address = "122 Downling Street",
      phoneNumber = "+44894928884",
      inProgress = false,
      errorMessage = "",
      showDatePicker = false,
      reminderDate = null,
    ),
    onNameChanged = { },
    onAddressChanged = { },
    onPhoneNumberChanged = { },
    onSaveClicked = { },
    onDateSelected = { },
    onDatePickerDismissed = { },
    onSelectDateClicked = { },
    formatDate = { "23 November 2034" },
    enableSaveButton = { true },
    modifier = Modifier,
  )
}