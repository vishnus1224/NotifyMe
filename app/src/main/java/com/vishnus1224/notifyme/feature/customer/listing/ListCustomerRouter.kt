package com.vishnus1224.notifyme.feature.customer.listing

import com.vishnus1224.notifyme.arch.GoToDestinationCommand
import com.vishnus1224.notifyme.arch.Router
import com.vishnus1224.notifyme.feature.customer.data.Customer
import com.vishnus1224.notifyme.navigation.createCustomerDetailsRoute
import javax.inject.Inject

class ListCustomerRouter
@Inject constructor(

) : Router() {

  fun goToCustomerDetails(customer: Customer) = sendCommand(
    GoToDestinationCommand(
      createCustomerDetailsRoute(
        customerId = customer.id,
      )
    )
  )

  fun goToAddNewCustomer() = sendCommand(
    GoToDestinationCommand(
      route = createCustomerDetailsRoute(
        customerId = null,
      )
    )
  )
}