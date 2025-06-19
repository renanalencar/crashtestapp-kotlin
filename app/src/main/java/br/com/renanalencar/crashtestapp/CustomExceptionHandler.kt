package br.com.renanalencar.crashtestapp

import android.content.Context
import android.content.Intent
import android.util.Log

class CustomExceptionHandler(
    private val context: Context,
) : Thread.UncaughtExceptionHandler {
    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(
        thread: Thread,
        throwable: Throwable,
    ) {
        Log.e("TESTTAG", "App crashed intentionally")
        restartApp(throwable)
        defaultHandler?.uncaughtException(thread, throwable) // Optional: Call the default handler
    }

    private fun restartApp(exception: Throwable) {
        val intent =
            Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_CRASH_OCCURRED, true)
                putExtra(EXTRA_CRASH_MESSAGE, exception.message ?: "Unknown error")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
        context.startActivity(intent)
        Runtime.getRuntime().exit(0) // Close the current process
    }

    companion object {
        const val EXTRA_CRASH_OCCURRED = "crash_occurred"
        const val EXTRA_CRASH_MESSAGE = "crash_message"
    }
}