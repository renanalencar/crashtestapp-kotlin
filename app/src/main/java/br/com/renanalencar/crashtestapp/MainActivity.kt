package br.com.renanalencar.crashtestapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.renanalencar.crashtestapp.ui.screen.CrashTestScreen
import br.com.renanalencar.crashtestapp.ui.theme.CrashTestAppTheme

class MainActivity : ComponentActivity() {
    private var crashCount by mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupExceptionHandler()
        handleRestartInfo()

        setContent {
            CrashTestAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    CrashTestScreen(
                        crashCount = crashCount,
                        onResetCounter = { resetCrashCounter() },
                        onTriggerCrash = { triggerCrash() },
                        onTriggerNullPointer = { triggerNullPointerException() },
                        onTriggerArrayIndex = { triggerArrayIndexException() },
                    )
                }
            }
        }
    }

    private fun setupExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(
            AutoRestartExceptionHandler(this),
        )
    }

    private fun handleRestartInfo() {
        if (intent.getBooleanExtra(AutoRestartExceptionHandler.EXTRA_CRASH_OCCURRED, false)) {
            crashCount++
            val crashMessage = intent.getStringExtra(AutoRestartExceptionHandler.EXTRA_CRASH_MESSAGE)
            showRestartMessage(crashMessage)
        }
    }

    private fun triggerCrash(): Unit = throw RuntimeException("Crash intencional disparado pelo usuário!")

    private fun triggerNullPointerException() {
        val nullString: String? = null
        nullString!!.length
    }

    private fun triggerArrayIndexException() {
        val array = arrayOf(1, 2, 3)
        array[10]
    }

    private fun resetCrashCounter() {
        crashCount = 0
        Toast.makeText(this, "Contador resetado", Toast.LENGTH_SHORT).show()
    }

    private fun showRestartMessage(crashMessage: String?) {
        val message = "App reiniciado após crash!\nMotivo: $crashMessage"

//        AlertDialog
//            .Builder(this)
//            .setTitle("Auto-Restart Executado")
//            .setMessage(message)
//            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
//            .show()

        Toast.makeText(this, "App reiniciado automaticamente", Toast.LENGTH_LONG).show()
    }
}