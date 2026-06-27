package com.example.waterreminderpackage com.example.waterremPeriodicWork(
            "water_reminder",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1001
            )
        }
    }
}

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.JavascriptInterface
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*

import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        setContentView(webView)

        // WebView setup
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        // JS bridge
        webView.addJavascriptInterface(WebAppInterface(), "Android")

        // Load HTML from assets
        webView.loadUrl("file:///android_asset/Index.html")

        requestNotificationPermission()
    }

    // Interface exposed to JavaScript
    inner class WebAppInterface {

        @JavascriptInterface
        fun startReminder() {
            scheduleReminder()
        }
    }

    private fun scheduleReminder() {
        val workRequest =
            PeriodicWorkRequestBuilder<WaterReminderWorker>(
                1, TimeUnit.HOURS
            ).build()

