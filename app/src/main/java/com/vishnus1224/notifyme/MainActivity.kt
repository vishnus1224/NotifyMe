package com.vishnus1224.notifyme

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vishnus1224.notifyme.feature.customer.ui.AddCustomerScreen
import com.vishnus1224.notifyme.feature.customer.ui.ListCustomerScreen
import com.vishnus1224.notifyme.feature.home.HomeState
import com.vishnus1224.notifyme.feature.home.HomeViewModel
import com.vishnus1224.notifyme.ui.theme.NotifyMeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotifyMeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val state: State<HomeState> = homeViewModel.state().collectAsStateWithLifecycle()

                    when (state.value) {
                        is HomeState.AddCustomer -> ShowAddCustomerScreen()
                        is HomeState.CustomerDetails -> ShowCustomerDetailsScreen()
                        is HomeState.CustomerList -> ShowCustomerListScreen()
                    }
                }
            }
        }

        /*onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(this@MainActivity, "nacl pressed", Toast.LENGTH_SHORT).show()
            }

        })*/
    }

    @Composable
    private fun ShowAddCustomerScreen() {
        AddCustomerScreen(
            viewModel = viewModel(),
            navigator = homeViewModel::onNavigate,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        )
    }

    @Composable
    private fun ShowCustomerListScreen() {
        ListCustomerScreen(
            viewModel = viewModel(),
            navigator = homeViewModel::onNavigate,
            modifier = Modifier.fillMaxSize(),
        )
    }

    @Composable
    private fun ShowCustomerDetailsScreen() {
        AddCustomerScreen(
            viewModel = viewModel(),
            navigator = homeViewModel::onNavigate,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        )
    }
}