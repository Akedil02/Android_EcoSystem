package com.example.ecosystem.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecosystem.ui.components.AppIcon
import com.example.ecosystem.ui.components.Icon as EcoIconSpec
import com.example.ecosystem.ui.components.SectionHeader
import com.example.ecosystem.ui.theme.EcoSystemTheme
import com.example.ecosystem.ui.theme.Spacing

data class ProfileActivityUiState(
    val title: String,
    val detail: String,
    val isWasteDeposit: Boolean
)

data class ProfileUiState(
    val displayName: String,
    val role: String,
    val ecoCoins: String,
    val esgRating: String,
    val activities: List<ProfileActivityUiState>
)

@Composable
fun ProfileRoute(
    onBackClick: () -> Unit,
    onTripsClick: () -> Unit
) {
    ProfileScreen(
        uiState = ProfileUiState(
            displayName = "Akedil",
            role = "KBTU Student",
            ecoCoins = "1,250",
            esgRating = "82/100",
            activities = listOf(
                ProfileActivityUiState("Waste Deposit", "+50 EcoCoins · Today", true),
                ProfileActivityUiState("Carpool", "+30 EcoCoins · Yesterday", false)
            )
        ),
        onBackClick = onBackClick,
        onTripsClick = onTripsClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onBackClick: () -> Unit,
    onTripsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick, modifier = Modifier.size(48.dp)) {
                        AppIcon(EcoIconSpec(Icons.AutoMirrored.Filled.ArrowBack, "Back to trips"))
                    }
                },
                title = { Text("Profile", style = MaterialTheme.typography.titleLarge) }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = onTripsClick,
                    icon = { AppIcon(EcoIconSpec(Icons.Filled.DirectionsCar)) },
                    label = { Text("Trips") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
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
            contentPadding = PaddingValues(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = Spacing.sm),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.large,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.size(72.dp)
                    ) {
                        AppIcon(
                            icon = EcoIconSpec(Icons.Filled.Person, "Profile avatar"),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(Spacing.md)
                        )
                    }
                    Text(
                        text = uiState.displayName,
                        modifier = Modifier.padding(top = Spacing.sm),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = uiState.role,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            item {
                SectionHeader(
                    title = "Your Green Impact",
                    subtitle = "A snapshot of your contribution"
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.md)
                ) {
                    ImpactCard(
                        value = uiState.ecoCoins,
                        label = "EcoCoins",
                        modifier = Modifier.weight(1f)
                    )
                    ImpactCard(
                        value = uiState.esgRating,
                        label = "ESG Rating",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item {
                SectionHeader(
                    title = "Recent Activity",
                    subtitle = "Your latest steps toward a greener campus"
                )
            }
            items(uiState.activities.size) { index ->
                val activity = uiState.activities[index]
                ListItem(
                    leadingContent = {
                        AppIcon(
                            EcoIconSpec(
                                if (activity.isWasteDeposit) Icons.Filled.Recycling
                                else Icons.Filled.DirectionsCar
                            )
                        )
                    },
                    headlineContent = { Text(activity.title) },
                    supportingContent = { Text(activity.detail) }
                )
            }
        }
    }
}

@Composable
private fun ImpactCard(value: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(Modifier.padding(Spacing.md)) {
            Text(value, style = MaterialTheme.typography.titleLarge)
            Text(label, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

private val previewProfile = ProfileUiState(
    displayName = "Akedil",
    role = "KBTU Student",
    ecoCoins = "1,250",
    esgRating = "82/100",
    activities = listOf(
        ProfileActivityUiState("Waste Deposit", "+50 EcoCoins · Today", true),
        ProfileActivityUiState("Carpool", "+30 EcoCoins · Yesterday", false)
    )
)

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    EcoSystemTheme {
        ProfileScreen(uiState = previewProfile, onBackClick = {}, onTripsClick = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProfileScreenDarkPreview() {
    EcoSystemTheme(darkTheme = true) {
        ProfileScreen(uiState = previewProfile, onBackClick = {}, onTripsClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ImpactCardPreview() {
    EcoSystemTheme {
        ImpactCard(value = "1,250", label = "EcoCoins")
    }
}
