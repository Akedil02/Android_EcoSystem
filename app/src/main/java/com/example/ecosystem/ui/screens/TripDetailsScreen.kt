package com.example.ecosystem.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Park
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecosystem.R
import com.example.ecosystem.data.sampleTrips
import com.example.ecosystem.ui.components.SectionHeader
import com.example.ecosystem.ui.theme.EcoSystemTheme
import com.example.ecosystem.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(
    tripId: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val trip = sampleTrips.find { it.id == tripId }
    var favorite by remember { mutableStateOf(false) }
    var joined by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick, modifier = Modifier.size(48.dp)) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to trips"
                        )
                    }
                },
                title = { Text("Trip Details", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(
                        onClick = { favorite = !favorite },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            if (favorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (favorite) "Remove trip from favorites" else "Add trip to favorites",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (trip != null) {
                Button(
                    onClick = { joined = !joined },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.md)
                        .height(48.dp)
                ) {
                    Text(if (joined) "Joined" else "Join Trip")
                }
            }
        }
    ) { innerPadding ->
        if (trip == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(Spacing.md),
                verticalArrangement = Arrangement.Center
            ) {
                SectionHeader(
                    title = "Trip not found",
                    subtitle = "This ride may no longer be available."
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(Spacing.md),
                verticalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                Image(
                    painter = painterResource(R.drawable.trip_route),
                    contentDescription = "Map illustration showing the shared ride route from " +
                        trip.pickupPoint + " to " + trip.destination,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(Spacing.md))
                )
                Text(
                    trip.title,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                SectionHeader(
                    title = "Trip overview",
                    subtitle = trip.category + " · Today, " + trip.time
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column {
                        ListItem(
                            leadingContent = {
                                Icon(Icons.Filled.LocationOn, contentDescription = null)
                            },
                            headlineContent = { Text(trip.pickupPoint) },
                            supportingContent = { Text("Pickup point") }
                        )
                        ListItem(
                            leadingContent = {
                                Icon(Icons.Filled.LocationOn, contentDescription = null)
                            },
                            headlineContent = { Text(trip.destination) },
                            supportingContent = { Text("Destination") }
                        )
                        ListItem(
                            leadingContent = { Icon(Icons.Filled.Park, contentDescription = null) },
                            headlineContent = { Text("Driver: " + trip.driver) },
                            supportingContent = {
                                Text(trip.seats.toString() + " seats open · " +
                                    trip.co2Saved.toString() + " kg CO₂ saved")
                            }
                        )
                    }
                }
                if (joined) {
                    Text(
                        "Your seat is saved in this demo.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TripDetailsScreenPreview() {
    EcoSystemTheme {
        TripDetailsScreen(tripId = 1, onBackClick = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TripDetailsScreenDarkPreview() {
    EcoSystemTheme(darkTheme = true) {
        TripDetailsScreen(tripId = 1, onBackClick = {})
    }
}
