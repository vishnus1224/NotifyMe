package com.vishnus1224.notifyme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vishnus1224.notifyme.navigation.ROUTE_CUSTOMER_LIST
import com.vishnus1224.notifyme.navigation.customerGraph
import com.vishnus1224.notifyme.navigation.extension.executeNavigationCommand
import com.vishnus1224.notifyme.ui.theme.NotifyMeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val navController = rememberNavController()

      NotifyMeTheme {

        NavHost(
          navController = navController,
          startDestination = ROUTE_CUSTOMER_LIST,
        ) {

          customerGraph(
            navigator = { navController.executeNavigationCommand(it) },
          )

        }
      }
    }
  }
}
