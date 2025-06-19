package br.com.renanalencar.crashtestapp

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat

class AppRestartService : Service() {
    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        val restartIntent =
            Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                putExtra(AutoRestartExceptionHandler.EXTRA_CRASH_OCCURRED, true)
                putExtra(AutoRestartExceptionHandler.EXTRA_CRASH_MESSAGE, intent?.getStringExtra("crash_message") ?: "Erro desconhecido")
            }

        val pendingIntent =
            PendingIntent.getActivity(
                this,
                0,
                restartIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT,
            )

        // Notificação obrigatória para Android 14+
        val channelId = "app_restart_channel"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            NotificationChannel(
                channelId,
                "Reinício do app",
                NotificationManager.IMPORTANCE_LOW,
            )
        notificationManager.createNotificationChannel(channel)

        val notification =
            NotificationCompat
                .Builder(this, channelId)
                .setContentTitle("Reiniciando o app")
                .setContentText("O aplicativo será reiniciado após um erro.")
                .setSmallIcon(R.drawable.ic_launcher_foreground) // ícone padrão ou personalizado
                .build()

        startForeground(1, notification)

        Handler(Looper.getMainLooper()).postDelayed({
            pendingIntent.send()
            stopSelf()
        }, 2000L)

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}