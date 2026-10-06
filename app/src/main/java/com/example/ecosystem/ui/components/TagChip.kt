package com.example.ecosystem.ui.components

import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.heightIn
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecosystem.ui.theme.EcoSystemTheme

@Composable
fun TagChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1
            )
        },
        modifier = modifier.heightIn(min = 48.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun TagChipPreview() {
    EcoSystemTheme {
        TagChip(text = "University", selected = true, onClick = {})
    }
}
