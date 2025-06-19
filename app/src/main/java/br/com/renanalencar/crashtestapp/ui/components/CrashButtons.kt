package br.com.renanalencar.crashtestapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CrashButtons(
    onTriggerCrash: () -> Unit,
    onTriggerNullPointer: () -> Unit,
    onTriggerArrayIndex: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        CrashButton(
            text = "Gerar RuntimeException",
            onClick = onTriggerCrash,
            backgroundColor = Color(0xFFD32F2F),
        )

        CrashButton(
            text = "Gerar NullPointerException",
            onClick = onTriggerNullPointer,
            backgroundColor = Color(0xFFFF9800),
        )

        CrashButton(
            text = "Gerar ArrayIndexOutOfBoundsException",
            onClick = onTriggerArrayIndex,
            backgroundColor = Color(0xFF9C27B0),
        )
    }
}