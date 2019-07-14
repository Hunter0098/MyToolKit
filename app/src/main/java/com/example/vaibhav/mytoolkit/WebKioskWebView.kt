package com.example.vaibhav.mytoolkit

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*
import android.widget.Toast
import kotlinx.android.synthetic.main.web_kiosk_web_view.*

class WebKioskWebView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_kiosk_web_view)

        val backActionbar = supportActionBar
        backActionbar!!.title = "WebKiosk"
        backActionbar!!.setDisplayHomeAsUpEnabled(true)

        webkioskWebViewActivity.webViewClient = MyWebViewClient(this)
        webkioskWebViewActivity.settings.javaScriptEnabled = true
        webkioskWebViewActivity.settings.loadWithOverviewMode = true
        webkioskWebViewActivity.settings.useWideViewPort = true
        webkioskWebViewActivity.settings.builtInZoomControls = true
        webkioskWebViewActivity.settings.displayZoomControls = true
        webkioskWebViewActivity.loadUrl("https://webkiosk.jiit.ac.in")
    }

    class MyWebViewClient(val activity: Activity) : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url = request?.url.toString()
            view?.loadUrl(url)
            return true
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            Toast.makeText(activity, "Error while loading the page!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
