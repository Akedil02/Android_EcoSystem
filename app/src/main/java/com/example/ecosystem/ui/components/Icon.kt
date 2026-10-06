package com.example.ecosystem.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Park
import androidx.compose.material3.Icon as MaterialIcon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecosystem.ui.theme.EcoSystemTheme

data class Icon(
    val imageVector: ImageVector,
    val contentDescription: String? = null
)

@Composable
fun AppIcon(
    icon: Icon,
    modifier: Modifier = Modifier,
    tint: Color? = null
) {
    MaterialIcon(
        imageVector = icon.imageVector,
        contentDescription = icon.contentDescription,
        modifier = modifier,
        tint = tint ?: LocalContentColor.current
    )
}

@Preview(showBackground = true)
@Composable
private fun AppIconPreview() {
    EcoSystemTheme {
        AppIcon(Icon(imageVector = Icons.Filled.Park, contentDescription = "Green impact"))
    }
}
