package com.example.afn_momogi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay 2 detik sebelum pindah halaman
        Handler(Looper.getMainLooper()).postDelayed({
            
            // Baca SharedPreferences untuk mengecek status login
            val sharedPref = getSharedPreferences("GmsPrefs", Context.MODE_PRIVATE)
            val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

            if (isLoggedIn) {
                // Kalau sudah login, langsung ke Beranda (MainActivity)
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // Kalau belum, suruh Login dulu
                startActivity(Intent(this, LoginActivity::class.java))
            }
            
            finish() // Tutup halaman Splash
        }, 2000)
    }
}
