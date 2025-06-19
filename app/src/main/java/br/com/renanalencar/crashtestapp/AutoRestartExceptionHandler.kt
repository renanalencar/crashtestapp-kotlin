package br.com.renanalencar.crashtestapp

import android.app.Activity
import android.app.ActivityOptions
import android.app.ActivityOptions.MODE_BACKGROUND_ACTIVITY_START_ALLOW_ALWAYS
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

class AutoRestartExceptionHandler(
    private val activity: Activity,
) : Thread.UncaughtExceptionHandler {
    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(
        thread: Thread,
        exception: Throwable,
    ) {
        try {
            restartApplicationWithPermissions(exception)
        } catch (e: Exception) {
            defaultHandler?.uncaughtException(thread, exception)
        }
    }

    private fun restartApplicationWithPermissions(exception: Throwable) {
        val restartIntent =
            Intent(activity, MainActivity::class.java).apply {
                putExtra(EXTRA_CRASH_OCCURRED, true)
                putExtra(EXTRA_CRASH_MESSAGE, exception.message ?: "Erro desconhecido")
                addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK,
                )
            }

        // Configurações específicas para Android 14+
        val activityOptions =
            ActivityOptions.makeBasic().apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    setPendingIntentCreatorBackgroundActivityStartMode(
                        MODE_BACKGROUND_ACTIVITY_START_ALLOW_ALWAYS,
                    )
                }
            }

        val pendingIntent =
            PendingIntent.getActivity(
                CrashTestApplication.instance,
                RESTART_REQUEST_CODE,
                restartIntent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE,
                activityOptions?.toBundle(),
            )

        val alarmManager =
            CrashTestApplication.instance
                .getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.set(
            AlarmManager.RTC,
            System.currentTimeMillis() + RESTART_DELAY_MS,
            pendingIntent,
        )

        activity.finish()
        System.exit(2)
    }

    companion object {
        const val EXTRA_CRASH_OCCURRED = "crash_occurred"
        const val EXTRA_CRASH_MESSAGE = "crash_message"
        private const val RESTART_REQUEST_CODE = 1001
        private const val RESTART_DELAY_MS = 2000L
    }
}