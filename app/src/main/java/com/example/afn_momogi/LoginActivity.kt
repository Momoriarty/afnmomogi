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
            // Ambil teks langsung lewat binding
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            // Validasi: Username harus sama dengan Password dan tidak boleh kosong
            if (username == password && username.isNotEmpty()) {
                // 1. Simpan status sesi ke SharedPreferences
                val sharedPref = getSharedPreferences("GmsPrefs", Context.MODE_PRIVATE)
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
    }
}