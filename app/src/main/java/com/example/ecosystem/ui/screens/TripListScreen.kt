package com.example.ecosystem.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecosystem.data.sampleTrips
import com.example.ecosystem.ui.components.AppIcon
import com.example.ecosystem.ui.components.Icon as EcoIconSpec
import com.example.ecosystem.ui.components.SectionHeader
import com.example.ecosystem.ui.components.TagChip
import com.example.ecosystem.ui.components.TripCard
import com.example.ecosystem.ui.theme.EcoSystemTheme
import com.example.ecosystem.ui.theme.Spacing

private val tripCategories = listOf("All", "University", "City", "Morning", "Evening")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripListScreen(
    onTripClick: (Int) -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    val filteredTrips = remember(selectedCategory, searchQuery) {
        sampleTrips.filter { trip ->
            val matchesCategory = selectedCategory == "All" || trip.category == selectedCategory
            val matchesQuery = searchQuery.isBlank() ||
                trip.title.contains(searchQuery, ignoreCase = true) ||
                trip.pickupPoint.contains(searchQuery, ignoreCase = true) ||
                trip.destination.contains(searchQuery, ignoreCase = true) ||
                trip.driver.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }

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
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = {
                        AppIcon(
                            EcoIconSpec(Icons.Filled.Search),
                        )
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
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category }
                        )
                    }
                }
            }
            item(key = "results-heading") {
                SectionHeader(
                    title = "Recommended",
                    subtitle = if (filteredTrips.isEmpty()) {
                        "No matching rides for this search"
                    } else {
                        filteredTrips.size.toString() + " rides available"
                    }
                )
            }
            if (filteredTrips.isEmpty()) {
                item(key = "empty-state") {
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
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AppIcon(
                                icon = EcoIconSpec(Icons.Filled.Search),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.height(Spacing.sm))
                            Text(
                                text = "No trips found",
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(Spacing.xs))
                            Text(
                                text = "Try another category or search for a different destination.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                items(filteredTrips, key = { it.id }) { trip ->
                    TripCard(
                        trip = trip,
                        onClick = { onTripClick(trip.id) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TripListScreenPreview() {
    EcoSystemTheme {
        TripListScreen(onTripClick = {}, onProfileClick = {})
    }
}
