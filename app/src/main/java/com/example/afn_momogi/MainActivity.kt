package com.example.afn_momogi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.afn_momogi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Tampilkan HomeFragment secara default saat aplikasi dibuka
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // 2. Pasang Listener saat tab Bottom Navigation diklik
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_beranda -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.nav_jadwal -> {
                    replaceFragment(JadwalFragment())
                    true
                }

                R.id.nav_pembayaran -> {
                    replaceFragment(PembayaranFragment())
                    true
                }

                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }
    }

    // Fungsi sakti untuk menukar Fragment di dalam container
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}