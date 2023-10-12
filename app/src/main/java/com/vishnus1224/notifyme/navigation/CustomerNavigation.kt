package com.vishnus1224.notifyme.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vishnus1224.notifyme.arch.NavigationCommand
import com.vishnus1224.notifyme.feature.customer.detail.CustomerDetailsScreen
import com.vishnus1224.notifyme.feature.customer.listing.ListCustomerScreen
import com.vishnus1224.notifyme.navigation.extension.appendOptionalArgument
import com.vishnus1224.notifyme.navigation.extension.appendSeparatorChar

// Arguments
private const val ARG_CUSTOMER_ID = "customerId"

// Routes
private const val ROUTE_CUSTOMER = "customer"
const val ROUTE_CUSTOMER_LIST = "$ROUTE_CUSTOMER/list"
private const val ROUTE_CUSTOMER_DETAIL = "$ROUTE_CUSTOMER/details?$ARG_CUSTOMER_ID={$ARG_CUSTOMER_ID}"

fun createCustomerDetailsRoute(customerId: String?): String {
  val routeBuilder = StringBuilder(ROUTE_CUSTOMER)
  routeBuilder.appendSeparatorChar()
  routeBuilder.append("details")
  routeBuilder.appendOptionalArgument(ARG_CUSTOMER_ID, customerId)

  return routeBuilder.toString()
}

// Graph
fun NavGraphBuilder.customerGraph(
  navigator: (NavigationCommand) -> Unit,
) {

    customerListScreen(
      navigator = navigator,
      modifier = Modifier.fillMaxSize(),
    )

    customerDetailsScreen(
      navigator = navigator,
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
    )
}

// Destinations
private fun NavGraphBuilder.customerListScreen(
  navigator: (NavigationCommand) -> Unit,
  modifier: Modifier = Modifier
) {
  composable(
    route = ROUTE_CUSTOMER_LIST,
  ) {
    ListCustomerScreen(
      viewModel = hiltViewModel(),
      navigator = navigator,
      modifier = modifier,
    )
  }
}

private fun NavGraphBuilder.customerDetailsScreen(
  navigator: (NavigationCommand) -> Unit,
  modifier: Modifier = Modifier,
) {
  composable(
    route = ROUTE_CUSTOMER_DETAIL,
    arguments = listOf(
      navArgument(ARG_CUSTOMER_ID) {
        type = NavType.StringType
        nullable = true
      }
    ),
  ) {
    CustomerDetailsScreen(
      viewModel = hiltViewModel(),
      navigator = navigator,
      modifier = modifier,
    )
  }
}