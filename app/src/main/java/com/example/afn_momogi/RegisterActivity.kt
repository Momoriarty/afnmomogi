package com.example.afn_momogi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.afn_momogi.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSelanjutnya.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()

            // Ambil tanggal dari DatePicker
            val day = binding.datePicker.dayOfMonth
            val month = binding.datePicker.month + 1
            val year = binding.datePicker.year
            val tanggalLahir = "$day/$month/$year"

            // Ambil jenis kelamin dari RadioButton yang dipilih
            val selectedGenderId = binding.rgJenisKelamin.checkedRadioButtonId
            val selectedGenderButton = binding.root.findViewById<RadioButton>(selectedGenderId)
            val jenisKelamin = selectedGenderButton?.text?.toString() ?: ""

            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()


            if (password != confirmPassword) {
                Toast.makeText(this, "Password dan Confirm Password tidak cocok!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPref = getSharedPreferences("GmsPrefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("nama", nama)
                putString("email", email)
                putString("tanggalLahir", tanggalLahir)
                putString("jenisKelamin", jenisKelamin)
                putString("username", username)
                putString("password", password)
                putString("confirmPassword", confirmPassword)
                apply()
            }

            // 3. Pindah ke VerifikasiActivity
            val intent = Intent(this, VerifikasiActivity::class.java)
            startActivity(intent)
        }
    }
}