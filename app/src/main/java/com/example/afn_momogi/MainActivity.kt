package com.example.afn_momogi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Setup Logout (Profile Icon) menggunakan SharedPreferences
        val btnProfile = findViewById<android.widget.ImageView>(R.id.btnProfile)
        btnProfile.setOnClickListener {
            // Hapus status login
            val sharedPref = getSharedPreferences("GmsPrefs", android.content.Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                clear()
                apply()
            }
            
            // Kembali ke halaman Login
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Setup Button ke Halaman Web
        val btnOpenWeb = findViewById<Button>(R.id.btnOpenWeb)
        btnOpenWeb.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            startActivity(intent)
        }

        // Setup Button ke Halaman Detail (Quick Actions)
        val btnOpenDetail = findViewById<android.view.View>(R.id.btnOpenDetail)
        btnOpenDetail.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }

        // Setup Tab Jadwal di Bottom Navigation
        val tabJadwal = findViewById<android.view.View>(R.id.tabJadwal)
        tabJadwal.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}