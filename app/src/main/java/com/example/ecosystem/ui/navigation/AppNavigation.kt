package com.example.ecosystem.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ecosystem.ui.screens.ProfileScreen
import com.example.ecosystem.ui.screens.TripDetailsScreen
import com.example.ecosystem.ui.screens.TripListScreen

private object Destinations {
    const val Trips = "trips"
    const val TripDetails = "trip/{tripId}"
    const val Profile = "profile"

    fun tripDetails(tripId: Int) = "trip/$tripId"
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Trips,
        modifier = modifier
    ) {
        composable(Destinations.Trips) {
            TripListScreen(
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
            TripDetailsScreen(
                tripId = tripId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Destinations.Profile) {
            ProfileScreen(
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
