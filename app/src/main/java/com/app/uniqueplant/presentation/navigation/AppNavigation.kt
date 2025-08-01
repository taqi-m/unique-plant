package com.app.uniqueplant.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.uniqueplant.data.datasource.preferences.SharedPreferencesRepository
import com.app.uniqueplant.presentation.admin.home.HomeScreen
import com.app.uniqueplant.presentation.admin.home.HomeViewModel
import com.app.uniqueplant.presentation.auth.AuthScreen
import com.app.uniqueplant.presentation.auth.AuthViewModel
import com.app.uniqueplant.presentation.transactions.AddTransactionScreen
import com.app.uniqueplant.presentation.transactions.AddTransactionViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    prefs: SharedPreferencesRepository
) {

    NavHost(
        navController = navController,
        startDestination = if (prefs.isUserLoggedIn()) {
            when (prefs.getUserType()) {
                "admin" -> MainScreens.AdminHome.route
                "employee" -> MainScreens.EmployeeHome.route
                else -> MainScreens.Auth.route
            }
        } else {
            MainScreens.Auth.route
        }
    ) {

        composable(route = MainScreens.Auth.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            val authState by authViewModel.state.collectAsState()

            AuthScreen(
                appNavController = navController,
                state = authState,
                onEvent = authViewModel::onEvent,
/*                onLoginSuccess = {
                    navController.navigate(MainScreens.Home.route) {
                        popUpTo(MainScreens.Auth.route) { inclusive = true }
                    }
                }*/
            )
        }

        composable(route = MainScreens.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val homeState by homeViewModel.state.collectAsState()
            HomeScreen(
                appNavController = navController,
                state = homeState,
                onEvent = homeViewModel::onEvent
            )
        }

        composable(route = MainScreens.EmployeeHome.route) {
            // User home screen content
        }

        composable(route = MainScreens.AdminHome.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val homeState by homeViewModel.state.collectAsState()
            HomeScreen(
                appNavController = navController,
                state = homeState,
                onEvent = homeViewModel::onEvent
            )
        }

        composable(route = MainScreens.AddTransaction.route) {
            val addTransactionViewModel: AddTransactionViewModel = hiltViewModel()
            val addTransactionState by addTransactionViewModel.state.collectAsState()
            AddTransactionScreen(
                appNavController = navController,
                state = addTransactionState,
                onEvent = addTransactionViewModel::onEvent
            )
        }
    }
}
