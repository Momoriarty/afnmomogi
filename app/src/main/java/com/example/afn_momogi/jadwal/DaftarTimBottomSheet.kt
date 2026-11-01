package com.example.afn_momogi.jadwal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.afn_momogi.MainActivity
import com.example.afn_momogi.R
import com.example.afn_momogi.databinding.FragmentDaftarTimBottomSheetBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DaftarTimBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentDaftarTimBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaftarTimBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmitPendaftaran.setOnClickListener {
            val namaTim = binding.etNamaTim.text.toString().trim()
            val kapten = binding.etNamaKapten.text.toString().trim()
            val wa = binding.etNoWa.text.toString().trim()

            if (namaTim.isEmpty() || kapten.isEmpty() || wa.isEmpty()) {
                Toast.makeText(requireContext(), "Semua data wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Notifikasi Berhasil
            Toast.makeText(requireContext(), "Pendaftaran $namaTim Berhasil! Mengalihkan ke pembayaran...", Toast.LENGTH_LONG).show()
            dismiss() // Menutup form meluncur

            // Otomatis pindahkan layar ke Tab Pembayaran
            (requireActivity() as? MainActivity)?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.selectedItemId = R.id.nav_pembayaran
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}