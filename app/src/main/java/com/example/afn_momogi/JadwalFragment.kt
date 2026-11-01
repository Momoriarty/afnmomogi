package com.example.afn_momogi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afn_momogi.databinding.FragmentJadwalBinding
import com.example.afn_momogi.jadwal.DaftarTimBottomSheet

class JadwalFragment : Fragment() {

    private var _binding: FragmentJadwalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJadwalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Klik tombol Daftar Card 1 -> Munculkan Bottom Sheet
        binding.btnDaftar1.setOnClickListener {
            val bottomSheet = DaftarTimBottomSheet()
            bottomSheet.show(parentFragmentManager, "DaftarTimBottomSheet")
        }

        // Klik tombol Daftar Card 2 -> Munculkan Bottom Sheet
        binding.btnDaftar2.setOnClickListener {
            val bottomSheet = DaftarTimBottomSheet()
            bottomSheet.show(parentFragmentManager, "DaftarTimBottomSheet")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}