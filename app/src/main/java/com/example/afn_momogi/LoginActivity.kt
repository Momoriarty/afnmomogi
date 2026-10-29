package com.example.afn_momogi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.afn_momogi.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar & Tombol Back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnLogin.setOnClickListener {
            // Ambil input dari user
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Ambil data yang tersimpan di SharedPreferences
            val sharedPref = getSharedPreferences("GmsPrefs", Context.MODE_PRIVATE)
            val savedUsername = sharedPref.getString("username", "") ?: ""
            val savedPassword = sharedPref.getString("password", "") ?: ""

            // Rule 1: jika username = password
            val isRule1 = username.isNotEmpty() && username == password

            // Rule 2: jika username dan password sesuai dengan yang tersimpan di SharedPreferences
            val isRule2 = username.isNotEmpty() && username == savedUsername && password == savedPassword

            // Cek apakah memenuhi Rule 1 ATAU Rule 2
            if (isRule1 || isRule2) {
                // 1. Simpan status sesi ke SharedPreferences
                with(sharedPref.edit()) {
                    putBoolean("isLoggedIn", true)
                    putString("username", username)
                    apply()
                }

                // 2. Berpindah ke MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Tampilkan AlertDialog jika kredensial salah
                AlertDialog.Builder(this)
                    .setTitle("Gagal")
                    .setMessage("Silahkan coba lagi")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }

        binding.btnDaftar.setOnClickListener {
            // Berpindah ke RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}