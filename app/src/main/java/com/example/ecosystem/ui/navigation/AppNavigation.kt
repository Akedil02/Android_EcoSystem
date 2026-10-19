package com.example.ecosystem.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ecosystem.ui.screens.ProfileRoute
import com.example.ecosystem.ui.screens.TripDetailsRoute
import com.example.ecosystem.ui.screens.TripListRoute
import com.example.ecosystem.ui.viewmodel.TripListViewModel

private object Destinations {
    const val Trips = "trips"
    const val TripDetails = "trip/{tripId}"
    const val Profile = "profile"

    fun tripDetails(tripId: Int) = "trip/$tripId"
}

@Composable
fun AppNavigation(
    viewModel: TripListViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Trips,
        modifier = modifier
    ) {
        composable(Destinations.Trips) {
            TripListRoute(
                viewModel = viewModel,
                onTripClick = { tripId ->
                    navController.navigate(Destinations.tripDetails(tripId))
                },
                onProfileClick = {
                    navController.navigate(Destinations.Profile) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(
            route = Destinations.TripDetails,
            arguments = listOf(navArgument("tripId") { type = NavType.IntType })
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getInt("tripId") ?: -1
            TripDetailsRoute(
                tripId = tripId,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Destinations.Profile) {
            ProfileRoute(
                onBackClick = {
                    if (!navController.popBackStack()) {
                        navController.navigate(Destinations.Trips) {
                            launchSingleTop = true
                        }
                    }
                },
                onTripsClick = {
                    navController.navigate(Destinations.Trips) {
                        popUpTo(Destinations.Trips) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
