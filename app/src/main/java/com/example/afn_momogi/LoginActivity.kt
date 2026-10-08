package com.example.afn_momogi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val btnLogin = findViewById<android.widget.Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            // Simpan status login ke SharedPreferences
            val sharedPref = getSharedPreferences("GmsPrefs", android.content.Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putBoolean("isLoggedIn", true)
                apply()
            }

            val intent = android.content.Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
