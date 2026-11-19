package com.example.afn_momogi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.afn_momogi.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    private val onboardingList = listOf(
        OnboardingItem(
            "Garuda Melayu Futsal",
            "Platform manajemen turnamen futsal modern di Riau. Cepat, transparan, dan terpercaya.",
            android.R.drawable.ic_menu_compass
        ),
        OnboardingItem(
            "Live Score & Klasemen",
            "Pantau skor pertandingan dan perolehan poin tim secara real-time tanpa ketinggalan momen.",
            android.R.drawable.ic_menu_today
        ),
        OnboardingItem(
            "Registrasi Tim Praktis",
            "Daftarkan squad tim futsalmu dan kelola pembayaran turnamen langsung dari aplikasi.",
            android.R.drawable.ic_menu_myplaces
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = OnboardingAdapter(onboardingList)
        binding.vpOnboarding.adapter = adapter
        binding.dotsIndicator.attachTo(binding.vpOnboarding)

        binding.vpOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == onboardingList.size - 1) {
                    binding.btnNext.text = "Selesai"
                    binding.btnSkip.visibility = android.view.View.INVISIBLE
                } else {
                    binding.btnNext.text = "Lanjut"
                    binding.btnSkip.visibility = android.view.View.VISIBLE
                }
            }
        })

        binding.btnNext.setOnClickListener {
            if (binding.vpOnboarding.currentItem + 1 < onboardingList.size) {
                binding.vpOnboarding.currentItem += 1
            } else {
                finish() // Menutup Onboarding dan kembali ke Profile
            }
        }

        binding.btnSkip.setOnClickListener {
            finish() // Menutup Onboarding dan kembali ke Profile
        }
    }
}