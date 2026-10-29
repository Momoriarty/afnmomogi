package com.example.afn_momogi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.afn_momogi.databinding.ActivityVerifikasiBinding

class VerifikasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifikasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVerifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Ambil data dari SharedPreferences
        val sharedPref = getSharedPreferences("GmsPrefs", Context.MODE_PRIVATE)
        val nama = sharedPref.getString("nama", "") ?: ""
        val email = sharedPref.getString("email", "") ?: ""
        val tanggalLahir = sharedPref.getString("tanggalLahir", "") ?: ""
        val jenisKelamin = sharedPref.getString("jenisKelamin", "") ?: ""
        val username = sharedPref.getString("username", "") ?: ""
        val password = sharedPref.getString("password", "") ?: ""
        val confirmPassword = sharedPref.getString("confirmPassword", "") ?: ""

        // 2. Tampilkan isian data ke layar
        binding.tvNama.text = "Nama: $nama"
        binding.tvEmail.text = "Email: $email"
        binding.tvTanggalLahir.text = "Tanggal Lahir: $tanggalLahir"
        binding.tvJenisKelamin.text = "Jenis Kelamin: $jenisKelamin"
        binding.tvUsername.text = "Username: $username"
        binding.tvPassword.text = "Password: $password"
        binding.tvConfirmPassword.text = "Confirm Password: $confirmPassword"

        // 3. Tombol Kembali: Menuju halaman registrasi untuk memperbaiki isian
        binding.btnKembali.setOnClickListener {
            finish()
        }

        // 4. Tombol Submit: Melakukan validasi data di SharedPreferences
        binding.btnSubmit.setOnClickListener {
            // Validasi 1: Semua inputan tidak boleh kosong
            if (nama.isEmpty() || email.isEmpty() || tanggalLahir.isEmpty() ||
                jenisKelamin.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Data tidak boleh ada yang kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi 2: Password dan Confirm Password harus sama
            if (password != confirmPassword) {
                Toast.makeText(this, "Password dan Confirm Password tidak cocok!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Jika lolos validasi: Tampilkan Toast Registrasi Berhasil & Navigasi
            Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_LONG).show()

            // Pindah ke halaman utama (ganti MainActivity dengan activity tujuan Anda)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}