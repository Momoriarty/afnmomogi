package com.example.afn_momogi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.afn_momogi.databinding.FragmentPembayaranBinding

class PembayaranFragment : Fragment() {

    private var _binding: FragmentPembayaranBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPembayaranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tombol Bayar Sekarang (Tagihan Pending)
        binding.btnBayarSekarang.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Pembayaran Tagihan")
                .setMessage("Bayar Sewa Lapangan Sintetis sebesar Rp 120.000 via QRIS?")
                .setPositiveButton("Bayar") { _, _ ->
                    Toast.makeText(requireContext(), "Pembayaran Berhasil Diverifikasi!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Nanti", null)
                .show()
        }

        // Tombol Lihat Resi 1
        binding.btnBukti1.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Resi Transaksi #INV-002")
                .setMessage("Item: Sewa Lapangan Vinyl\nNominal: Rp 150.000\nStatus: Lunas (QRIS/GoPay)\nTanggal: 18 Okt 2026")
                .setPositiveButton("Tutup", null)
                .show()
        }

        // Tombol Lihat Resi 2
        binding.btnBukti2.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Resi Transaksi #INV-001")
                .setMessage("Item: Registrasi Melayu Champions League\nNominal: Rp 250.000\nStatus: Lunas (Transfer BCA)\nTanggal: 14 Okt 2026")
                .setPositiveButton("Tutup", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}