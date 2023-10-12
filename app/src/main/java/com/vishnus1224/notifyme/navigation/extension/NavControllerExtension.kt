package com.vishnus1224.notifyme.navigation.extension

import androidx.navigation.NavController
import com.vishnus1224.notifyme.arch.GoToDestinationCommand
import com.vishnus1224.notifyme.arch.NavigationCommand

fun NavController.executeNavigationCommand(command: NavigationCommand) {

  when (command) {
    is GoToDestinationCommand -> navigate(command.route) {
      command.navigationOptions?.let {
        launchSingleTop = it.launchSingleTop
        if (it.popUpToRoute != null) popUpTo(it.popUpToRoute) {
          inclusive = false
        }
      }
    }
    else -> error("Cannot perform navigation for $command")
  }
}