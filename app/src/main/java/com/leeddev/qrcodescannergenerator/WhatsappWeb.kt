package com.leeddev.qrcodescannergenerator

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class WhatsappWeb : AppCompatActivity() {

    private lateinit var webView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whatsapp_web)

        webView = findViewById(R.id.webview)
        setTags()

        webView.loadUrl("https://web.whatsapp.com/")


    }

    private fun setTags() {

        webView.settings.javaScriptEnabled = true
        webView.settings.setAllowFileAccess(true);
        webView.settings.setAppCacheEnabled(true);

        webView.webViewClient = WebViewClient()
        webView.settings.useWideViewPort = true
        webView.webChromeClient = WebChromeClient()
        webView.settings.setGeolocationEnabled(true)
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.setSupportMultipleWindows(true)
        webView.settings.setNeedInitialFocus(true)
        webView.settings.loadWithOverviewMode = true
        webView.settings.blockNetworkImage = true
        webView.settings.builtInZoomControls = true
        webView.setInitialScale(100)

        webView.settings.userAgentString =
            "Mozilla/5.0 (Linux; Win64; x64; rv:46.0) Gecko/20100101 Firefox/68.0";
    }
}



