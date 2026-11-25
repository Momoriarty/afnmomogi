package com.example.afn_momogi.jadwal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.afn_momogi.databinding.FragmentDaftarTimBottomSheetBinding
import com.example.afn_momogi.network.SupabaseClient
import com.example.afn_momogi.network.TimItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

            val tim = TimItem(
                nama_tim = namaTim,
                nama_kapten = kapten,
                no_wa = wa
            )

            SupabaseClient.api.createTim(tim).enqueue(object : Callback<List<TimItem>> {
                override fun onResponse(call: Call<List<TimItem>>, response: Response<List<TimItem>>) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Tim $namaTim tersimpan!", Toast.LENGTH_SHORT).show()
                        parentFragmentManager.setFragmentResult("refresh_tim", Bundle())
                        dismiss()
                    } else {
                        Toast.makeText(requireContext(), "Gagal: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<TimItem>>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}