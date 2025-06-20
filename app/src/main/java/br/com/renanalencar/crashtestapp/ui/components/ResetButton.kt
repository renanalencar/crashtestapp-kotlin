package br.com.renanalencar.crashtestapp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ResetButton(onResetCounter: () -> Unit) {
    Button(
        onClick = onResetCounter,
        modifier =
            Modifier
                .fillMaxWidth()
                .height(56.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
            ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Reset",
            tint = Color.White,
            modifier = Modifier.size(20.dp),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Reset Counter",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = Color.White,
        )
    }
}

@Preview
@Composable
fun ResetButtonPreview() {
    ResetButton(onResetCounter = {})
}