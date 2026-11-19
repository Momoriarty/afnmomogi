package com.example.afn_momogi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.Toast
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

        // 1. Profil & Web
        binding.btnProfile.setOnClickListener {
            (requireActivity() as MainActivity).findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_nav_view).selectedItemId = R.id.nav_profile
        }

        binding.btnOpenWeb.setOnClickListener {
            startActivity(Intent(requireContext(), WebActivity::class.java))
        }

        // 2. Event Klik Tombol GridLayout
        binding.btnGridJadwal.setOnClickListener {
            (requireActivity() as MainActivity).findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_nav_view).selectedItemId = R.id.nav_jadwal
        }
        binding.btnGridTanding.setOnClickListener {
            Toast.makeText(requireContext(), "Mencari lawan tanding...", Toast.LENGTH_SHORT).show()
        }
        binding.btnGridTim.setOnClickListener {
            Toast.makeText(requireContext(), "Membuka daftar tim turnamen", Toast.LENGTH_SHORT).show()
        }
        binding.btnGridAturan.setOnClickListener {
            Toast.makeText(requireContext(), "Aturan: Wajib jersey bernomor punggung", Toast.LENGTH_SHORT).show()
        }

        // 3. Pasang 1 ListView dengan SimpleAdapter
        setupPengumumanListView()

        // 4. Pasang 1 RecyclerView Klasemen
        setupKlasemenRecyclerView()
    }

    private fun setupPengumumanListView() {
        val pengumumanData = listOf(
            mapOf("title" to "Registrasi Liga Futsal 2026 Dibuka", "desc" to "Pendaftaran tim dibuka hingga 25 November 2026."),
            mapOf("title" to "Perubahan Venue Matchday 6", "desc" to "Pertandingan semifinal dialihkan ke Riau Sport Center."),
            mapOf("title" to "Aturan Akumulasi Kartu", "desc" to "2 kartu kuning otomatis dilarang bermain 1 laga.")
        )

        val adapter = SimpleAdapter(
            requireContext(),
            pengumumanData,
            android.R.layout.simple_list_item_2,
            arrayOf("title", "desc"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        binding.lvPengumuman.adapter = adapter

        binding.lvPengumuman.setOnItemClickListener { _, _, position, _ ->
            val item = pengumumanData[position]
            Toast.makeText(requireContext(), "Info: ${item["title"]}", Toast.LENGTH_SHORT).show()
        }
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