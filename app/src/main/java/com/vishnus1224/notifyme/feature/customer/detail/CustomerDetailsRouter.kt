package com.vishnus1224.notifyme.feature.customer.detail

import com.vishnus1224.notifyme.arch.GoToDestinationCommand
import com.vishnus1224.notifyme.arch.NavigationOptions
import com.vishnus1224.notifyme.arch.Router
import com.vishnus1224.notifyme.navigation.ROUTE_CUSTOMER_LIST
import javax.inject.Inject

class CustomerDetailsRouter
@Inject constructor(

) : Router() {

    fun goToCustomerList() = sendCommand(
        GoToDestinationCommand(
            route = ROUTE_CUSTOMER_LIST,
            navigationOptions = NavigationOptions(
                launchSingleTop = true,
                popUpToRoute = ROUTE_CUSTOMER_LIST,
            )
        )
    )
}