package br.com.renanalencar.crashtestapp.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.renanalencar.crashtestapp.ui.components.AppTitle
import br.com.renanalencar.crashtestapp.ui.components.CrashButtons
import br.com.renanalencar.crashtestapp.ui.components.CrashCounter
import br.com.renanalencar.crashtestapp.ui.components.ResetButton
import br.com.renanalencar.crashtestapp.ui.components.WarningMessage
import br.com.renanalencar.crashtestapp.ui.theme.CrashTestAppTheme

@Composable
fun CrashTestScreen(
    crashCount: Int,
    onResetCounter: () -> Unit,
    onTriggerCrash: () -> Unit,
    onTriggerNullPointer: () -> Unit,
    onTriggerArrayIndex: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        AppTitle()

        Spacer(modifier = Modifier.height(32.dp))

        CrashCounter(crashCount = crashCount)

        Spacer(modifier = Modifier.height(24.dp))

        CrashButtons(
            onTriggerCrash = onTriggerCrash,
            onTriggerNullPointer = onTriggerNullPointer,
            onTriggerArrayIndex = onTriggerArrayIndex,
        )

        Spacer(modifier = Modifier.height(32.dp))

        ResetButton(onResetCounter = onResetCounter)

        Spacer(modifier = Modifier.height(24.dp))

        WarningMessage()
    }
}

@Preview(showBackground = true)
@Composable
fun CrashTestScreenPreview() {
    CrashTestAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            CrashTestScreen(
                crashCount = 3,
                onResetCounter = { },
                onTriggerCrash = { },
                onTriggerNullPointer = { },
                onTriggerArrayIndex = { },
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CrashTestScreenDarkPreview() {
    CrashTestAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            CrashTestScreen(
                crashCount = 0,
                onResetCounter = { },
                onTriggerCrash = { },
                onTriggerNullPointer = { },
                onTriggerArrayIndex = { },
            )
        }
    }
}