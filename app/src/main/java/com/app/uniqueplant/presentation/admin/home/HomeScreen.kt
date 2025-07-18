package com.app.uniqueplant.presentation.admin.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.uniqueplant.presentation.navigation.HomeBottomScreen
import com.app.uniqueplant.presentation.navigation.HomeNavGraph
import com.app.uniqueplant.presentation.navigation.MainScreens
import com.app.uniqueplant.ui.components.ExpandableFab
import com.app.uniqueplant.ui.components.FabOption
import com.app.uniqueplant.ui.theme.UniquePlantTheme

@Composable
fun HomeScreen(
    appNavController: NavHostController,
    state: HomeScreenState,
    onEvent: (HomeEvent) -> Unit = {},
) {
    val homeNavController = rememberNavController()
    val items = listOf(
        HomeBottomScreen.Dashboard,
        HomeBottomScreen.Transactions,
        HomeBottomScreen.Analytics
    )

    val currentRoute = homeNavController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painterResource(screen.iconResource),
                                contentDescription = null
                            )
                        },
                        label = { Text(screen.label) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                homeNavController.navigate(screen.route) {
                                    popUpTo(homeNavController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            val isExpanded = state.isFabExpanded
            ExpandableFab(
                isExpanded = isExpanded,
                onExpandToggle = { onEvent(HomeEvent.ToggleFabExpanded) },
                fabOptions = listOf(
                    FabOption(
                        icon = Icons.Filled.Add,
                        label = "Add Income",
                        onClick = {
                            appNavController.navigate(MainScreens.AddIncome.route)
                        }
                    ),
                    FabOption(
                        icon = Icons.Filled.Add,
                        label = "Add Expense",
                        onClick = {
                            appNavController.navigate(MainScreens.AddExpense.route)
                        }
                    )
                )
            )
            /*            AnimatedVisibility(
                            visible = when (currentRoute) {
                                HomeBottomScreen.Dashboard.route -> true
                                else -> false
                            },
                            enter = scaleIn(animationSpec = tween(ANIMATION_DURATION / 2)),
                            exit = scaleOut(animationSpec = tween(ANIMATION_DURATION / 2))
                        ) {

                        }*/
        }
    ) { paddingValues ->
        HomeNavGraph(homeNavController, appNavController, Modifier.padding(paddingValues))
    }
}


@Preview()
@Composable
fun HomeScreenPreview() {
    UniquePlantTheme {
        HomeScreen(
            appNavController = rememberNavController(),
            HomeScreenState(),
            onEvent = {}
        )
    }
}
