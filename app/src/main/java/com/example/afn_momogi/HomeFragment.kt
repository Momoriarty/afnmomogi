package com.example.afn_momogi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afn_momogi.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Klik Foto Profil -> Buka Tab Profile
        binding.btnProfile.setOnClickListener {
            (requireActivity() as MainActivity).findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_nav_view).selectedItemId = R.id.nav_profile
        }

        // 2. Klik Tombol Web -> Buka WebActivity
        binding.btnOpenWeb.setOnClickListener {
            val intent = Intent(requireContext(), WebActivity::class.java)
            startActivity(intent)
        }

        // 3. Klik Quick Action Detail -> Pindah ke Tab Jadwal
        binding.btnOpenDetail.setOnClickListener {
            (requireActivity() as MainActivity).findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_nav_view).selectedItemId = R.id.nav_jadwal
        }

        // 4. Inisialisasi RecyclerView Klasemen (FITUR BARU)
        setupKlasemenRecyclerView()
    }

    private fun setupKlasemenRecyclerView() {
        val dataKlasemen = listOf(
            KlasemenItem(1, "Garuda Melayu FC", 5, "+9", 13),
            KlasemenItem(2, "Vamos Riau FC", 5, "+6", 11),
            KlasemenItem(3, "PCR Futsal Club", 5, "+3", 9),
            KlasemenItem(4, "Melayu Pride FC", 5, "0", 6),
            KlasemenItem(5, "Pekanbaru United", 5, "-4", 3)
        )

        binding.rvKlasemen.layoutManager = LinearLayoutManager(requireContext())
        binding.rvKlasemen.adapter = KlasemenAdapter(dataKlasemen)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}