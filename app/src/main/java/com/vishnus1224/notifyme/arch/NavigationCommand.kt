package com.vishnus1224.notifyme.arch

interface NavigationCommand

data class GoToDestinationCommand(
    val route: String,
    val navigationOptions: NavigationOptions? = null,
) : NavigationCommand

data class NavigationOptions(
    val launchSingleTop: Boolean = false,
    val popUpToRoute: String? = null,
)