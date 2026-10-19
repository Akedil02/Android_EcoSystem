package com.example.ecosystem.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecosystem.data.model.Trip
import com.example.ecosystem.ui.components.AppIcon
import com.example.ecosystem.ui.components.Icon as EcoIconSpec
import com.example.ecosystem.ui.components.SectionHeader
import com.example.ecosystem.ui.components.TagChip
import com.example.ecosystem.ui.components.TripCard
import com.example.ecosystem.ui.theme.EcoSystemTheme
import com.example.ecosystem.ui.theme.Spacing
import com.example.ecosystem.ui.viewmodel.TripListViewModel

private val tripCategories = listOf("All", "University", "City", "Morning", "Evening")

@Composable
fun TripListRoute(
    viewModel: TripListViewModel,
    onTripClick: (Int) -> Unit,
    onProfileClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TripListScreen(
        uiState = uiState,
        onSearchChange = viewModel::onSearchChange,
        onCategorySelected = viewModel::onCategorySelected,
        onTripClick = onTripClick,
        onProfileClick = onProfileClick,
        onRetry = viewModel::onRetry
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripListScreen(
    uiState: TripListUiState,
    onSearchChange: (String) -> Unit,
    onCategorySelected: (String) -> Unit,
    onTripClick: (Int) -> Unit,
    onProfileClick: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Carpool", style = MaterialTheme.typography.titleLarge) })
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { AppIcon(EcoIconSpec(Icons.Filled.DirectionsCar)) },
                    label = { Text("Trips") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onProfileClick,
                    icon = { AppIcon(EcoIconSpec(Icons.Filled.Person)) },
                    label = { Text("Me") }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                start = Spacing.md,
                top = Spacing.md,
                end = Spacing.md,
                bottom = Spacing.lg
            ),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            item(key = "intro") {
                SectionHeader(
                    title = "Today's Trips",
                    subtitle = "Share a ride to KBTU and make every journey greener"
                )
            }
            item(key = "search") {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = {
                        AppIcon(EcoIconSpec(Icons.Filled.Search))
                    },
                    placeholder = {
                        Text(
                            text = "Search destination",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    shape = RoundedCornerShape(Spacing.md)
                )
            }
            item(key = "categories") {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                    contentPadding = PaddingValues(vertical = Spacing.xs)
                ) {
                    items(tripCategories, key = { it }) { category ->
                        TagChip(
                            text = category,
                            selected = uiState.selectedCategory == category,
                            onClick = { onCategorySelected(category) }
                        )
                    }
                }
            }
            item(key = "results-heading") {
                SectionHeader(
                    title = "Recommended",
                    subtitle = if (uiState.isLoading) {
                        "Loading local trips"
                    } else if (uiState.errorMessage != null) {
                        "Trips could not be loaded"
                    } else {
                        "${uiState.trips.size} rides available"
                    }
                )
            }
            when {
                uiState.isLoading -> item(key = "loading") {
                    StatusCard {
                        CircularProgressIndicator()
                        Spacer(Modifier.height(Spacing.sm))
                        Text("Loading trips…", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                uiState.errorMessage != null -> item(key = "error") {
                    StatusCard {
                        Text("Couldn't load trips", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = uiState.errorMessage,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Button(onClick = onRetry, modifier = Modifier.height(48.dp)) {
                            Text("Retry")
                        }
                    }
                }

                uiState.trips.isEmpty() -> item(key = "empty") {
                    StatusCard {
                        AppIcon(
                            icon = EcoIconSpec(Icons.Filled.Search),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(40.dp)
                        )
                        Text("No trips found", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "Try another category or search for a different destination.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                else -> items(uiState.trips, key = { it.id }) { trip ->
                    TripCard(trip = trip, onClick = { onTripClick(trip.id) })
                }
            }
        }
    }
}

@Composable
private fun StatusCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.lg),
            verticalArrangement = Arrangement.spacedBy(Spacing.sm),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

private val previewTrip = Trip(
    id = 1,
    title = "Morning ride from Dostyk Avenue to KBTU",
    driver = "Ayan",
    time = "08:30",
    seats = 3,
    co2Saved = 2.4,
    category = "University",
    pickupPoint = "Dostyk Avenue",
    destination = "KBTU"
)

@Preview(showBackground = true)
@Composable
private fun TripListLoadingPreview() {
    EcoSystemTheme {
        TripListScreen(
            uiState = TripListUiState(isLoading = true),
            onSearchChange = {}, onCategorySelected = {}, onTripClick = {},
            onProfileClick = {}, onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TripListContentPreview() {
    EcoSystemTheme {
        TripListScreen(
            uiState = TripListUiState(trips = listOf(previewTrip), isLoading = false),
            onSearchChange = {}, onCategorySelected = {}, onTripClick = {},
            onProfileClick = {}, onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TripListEmptyPreview() {
    EcoSystemTheme {
        TripListScreen(
            uiState = TripListUiState(isLoading = false),
            onSearchChange = {}, onCategorySelected = {}, onTripClick = {},
            onProfileClick = {}, onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TripListErrorPreview() {
    EcoSystemTheme {
        TripListScreen(
            uiState = TripListUiState(isLoading = false, errorMessage = "Please try again."),
            onSearchChange = {}, onCategorySelected = {}, onTripClick = {},
            onProfileClick = {}, onRetry = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TripListDarkPreview() {
    EcoSystemTheme(darkTheme = true) {
        TripListScreen(
            uiState = TripListUiState(trips = listOf(previewTrip), isLoading = false),
            onSearchChange = {}, onCategorySelected = {}, onTripClick = {},
            onProfileClick = {}, onRetry = {}
        )
    }
}
