package com.example.afn_momogi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afn_momogi.databinding.FragmentJadwalBinding
import com.example.afn_momogi.jadwal.DaftarTimBottomSheet
import com.example.afn_momogi.network.SupabaseClient
import com.example.afn_momogi.network.TimItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JadwalFragment : Fragment() {

    private var _binding: FragmentJadwalBinding? = null
    private val binding get() = _binding!!
    private val listTim = mutableListOf<TimItem>()
    private lateinit var adapter: TimAdapter

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

        // Setup RecyclerView untuk Read, Update, Delete tim
        adapter = TimAdapter(listTim) { item ->
            showAksiTimDialog(item)
        }
        binding.rvTim.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTim.adapter = adapter

        // Refresh list setelah DaftarTimBottomSheet berhasil simpan
        parentFragmentManager.setFragmentResultListener("refresh_tim", viewLifecycleOwner) { _, _ ->
            loadTim()
        }

        // READ: Muat data tim dari Supabase
        loadTim()

        // Klik tombol Daftar -> Munculkan Bottom Sheet (CREATE)
        binding.btnDaftar1.setOnClickListener {
            DaftarTimBottomSheet().show(parentFragmentManager, "DaftarTimBottomSheet")
        }
        binding.btnDaftar2.setOnClickListener {
            DaftarTimBottomSheet().show(parentFragmentManager, "DaftarTimBottomSheet")
        }
    }

    private fun loadTim() {
        SupabaseClient.api.getTim().enqueue(object : Callback<List<TimItem>> {
            override fun onResponse(call: Call<List<TimItem>>, response: Response<List<TimItem>>) {
                if (response.isSuccessful && _binding != null) {
                    listTim.clear()
                    listTim.addAll(response.body() ?: emptyList())
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<TimItem>>, t: Throwable) {
                if (isAdded) Toast.makeText(requireContext(), "Gagal muat tim: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showAksiTimDialog(item: TimItem) {
        val id = item.id ?: return
        val opsi = arrayOf("Edit Nama Tim (Update)", "Hapus Tim (Delete)")
        AlertDialog.Builder(requireContext())
            .setTitle(item.nama_tim)
            .setItems(opsi) { _, which ->
                when (which) {
                    0 -> showEditDialog(id, item.nama_tim)
                    1 -> hapusTim(id)
                }
            }
            .show()
    }

    private fun showEditDialog(id: Long, namaLama: String) {
        val etNama = EditText(requireContext()).apply { setText(namaLama) }
        AlertDialog.Builder(requireContext())
            .setTitle("Edit Nama Tim")
            .setView(etNama)
            .setPositiveButton("Simpan") { _, _ ->
                val namaBaru = etNama.text.toString().trim()
                if (namaBaru.isEmpty()) return@setPositiveButton
                SupabaseClient.api.updateTim("eq.$id", mapOf("nama_tim" to namaBaru))
                    .enqueue(object : Callback<List<TimItem>> {
                        override fun onResponse(call: Call<List<TimItem>>, response: Response<List<TimItem>>) {
                            if (response.isSuccessful) {
                                Toast.makeText(requireContext(), "Tim diperbarui!", Toast.LENGTH_SHORT).show()
                                loadTim()
                            }
                        }

                        override fun onFailure(call: Call<List<TimItem>>, t: Throwable) {
                            Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun hapusTim(id: Long) {
        SupabaseClient.api.deleteTim("eq.$id").enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Tim dihapus!", Toast.LENGTH_SHORT).show()
                    loadTim()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class TimAdapter(
        private val items: List<TimItem>,
        private val onClick: (TimItem) -> Unit
    ) : RecyclerView.Adapter<TimAdapter.VH>() {

        class VH(view: View) : RecyclerView.ViewHolder(view) {
            val tv1: TextView = view.findViewById(android.R.id.text1)
            val tv2: TextView = view.findViewById(android.R.id.text2)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val v = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_2, parent, false)
            return VH(v)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val item = items[position]
            holder.tv1.text = item.nama_tim
            holder.tv2.text = "Kapten: ${item.nama_kapten} • WA: ${item.no_wa}  (Tap untuk Edit/Hapus)"
            holder.itemView.setOnClickListener { onClick(item) }
        }

        override fun getItemCount(): Int = items.size
    }
}