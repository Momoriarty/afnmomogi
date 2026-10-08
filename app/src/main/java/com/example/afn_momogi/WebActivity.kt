package com.example.afn_momogi

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        // Setup Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Klasemen ASEAN Cup 2026"
        
        // Menampilkan tombol Back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val webView: WebView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient() // Biar buka di dalam aplikasi, bukan buka browser eksternal
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true // Bantu support website modern
        // Coba ganti ke pencarian Google buat ngetes
        webView.loadUrl("https://www.google.com/search?q=jadwal+futsal+asean+cup")
    }

    // Fungsi biar tombol back di toolbar jalan
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish() // Menutup halaman saat ini dan kembali ke halaman sebelumnya
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
