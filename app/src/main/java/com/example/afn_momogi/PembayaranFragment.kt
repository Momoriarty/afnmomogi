package com.example.afn_momogi

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afn_momogi.databinding.FragmentPembayaranBinding
import com.example.afn_momogi.network.PembayaranItem
import com.example.afn_momogi.network.SupabaseClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PembayaranFragment : Fragment() {

    private var _binding: FragmentPembayaranBinding? = null
    private val binding get() = _binding!!
    private val listPembayaran = mutableListOf<PembayaranItem>()
    private lateinit var adapter: PembayaranAdapter

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

        // Setup RecyclerView CRUD Supabase
        adapter = PembayaranAdapter(listPembayaran) { item ->
            showAksiDialog(item)
        }
        binding.rvPembayaran.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPembayaran.adapter = adapter

        // CREATE: Tambah Tagihan baru ke Supabase
        binding.btnTambahTagihan.setOnClickListener {
            showTambahDialog()
        }

        // READ: Muat data pembayaran dari Supabase
        loadPembayaran()

        // Tombol bawaan
        binding.btnBayarSekarang.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Pembayaran Tagihan")
                .setMessage("Bayar Slot Tim: Pekanbaru Futsal Series sebesar Rp 120.000 via QRIS?")
                .setPositiveButton("Bayar") { _, _ ->
                    Toast.makeText(requireContext(), "Pembayaran Berhasil Diverifikasi!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Nanti", null)
                .show()
        }

        binding.btnBukti1.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Resi Transaksi #INV-002")
                .setMessage("Item: Sewa Venue Pertandingan Semi-Final\nNominal: Rp 150.000\nStatus: Lunas (QRIS/GoPay)\nTanggal: 18 Okt 2026")
                .setPositiveButton("Tutup", null)
                .show()
        }

        binding.btnBukti2.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Resi Transaksi #INV-001")
                .setMessage("Item: Registrasi Melayu Champions League\nNominal: Rp 250.000\nStatus: Lunas (Transfer BCA)\nTanggal: 14 Okt 2026")
                .setPositiveButton("Tutup", null)
                .show()
        }
    }

    private fun loadPembayaran() {
        SupabaseClient.api.getPembayaran().enqueue(object : Callback<List<PembayaranItem>> {
            override fun onResponse(call: Call<List<PembayaranItem>>, response: Response<List<PembayaranItem>>) {
                if (response.isSuccessful && _binding != null) {
                    listPembayaran.clear()
                    listPembayaran.addAll(response.body() ?: emptyList())
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<PembayaranItem>>, t: Throwable) {
                if (isAdded) Toast.makeText(requireContext(), "Gagal muat: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showTambahDialog() {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 24, 48, 0)
        }
        val etJudul = EditText(requireContext()).apply { hint = "Judul Tagihan (misal: Daftar Turnamen)" }
        val etNominal = EditText(requireContext()).apply {
            hint = "Nominal (misal: 150000)"
            inputType = InputType.TYPE_CLASS_NUMBER
        }
        layout.addView(etJudul)
        layout.addView(etNominal)

        AlertDialog.Builder(requireContext())
            .setTitle("Tambah Tagihan")
            .setView(layout)
            .setPositiveButton("Simpan") { _, _ ->
                val judul = etJudul.text.toString().trim()
                val nominal = etNominal.text.toString().trim().toIntOrNull() ?: 0
                if (judul.isEmpty() || nominal <= 0) {
                    Toast.makeText(requireContext(), "Isi judul dan nominal yang valid!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                val item = PembayaranItem(judul = judul, nominal = nominal)
                SupabaseClient.api.createPembayaran(item).enqueue(object : Callback<List<PembayaranItem>> {
                    override fun onResponse(call: Call<List<PembayaranItem>>, response: Response<List<PembayaranItem>>) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Tagihan ditambahkan!", Toast.LENGTH_SHORT).show()
                            loadPembayaran()
                        }
                    }

                    override fun onFailure(call: Call<List<PembayaranItem>>, t: Throwable) {
                        Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showAksiDialog(item: PembayaranItem) {
        val id = item.id ?: return
        val opsi = arrayOf("Tandai Lunas (Update)", "Hapus Tagihan (Delete)")
        AlertDialog.Builder(requireContext())
            .setTitle(item.judul)
            .setItems(opsi) { _, which ->
                when (which) {
                    0 -> { // UPDATE
                        SupabaseClient.api.updatePembayaran("eq.$id", mapOf("status" to "Lunas"))
                            .enqueue(object : Callback<List<PembayaranItem>> {
                                override fun onResponse(call: Call<List<PembayaranItem>>, response: Response<List<PembayaranItem>>) {
                                    if (response.isSuccessful) {
                                        Toast.makeText(requireContext(), "Status diubah jadi Lunas!", Toast.LENGTH_SHORT).show()
                                        loadPembayaran()
                                    }
                                }

                                override fun onFailure(call: Call<List<PembayaranItem>>, t: Throwable) {
                                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                                }
                            })
                    }
                    1 -> { // DELETE
                        SupabaseClient.api.deletePembayaran("eq.$id")
                            .enqueue(object : Callback<Void> {
                                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                    if (response.isSuccessful) {
                                        Toast.makeText(requireContext(), "Tagihan dihapus!", Toast.LENGTH_SHORT).show()
                                        loadPembayaran()
                                    }
                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {
                                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                                }
                            })
                    }
                }
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class PembayaranAdapter(
        private val items: List<PembayaranItem>,
        private val onClick: (PembayaranItem) -> Unit
    ) : RecyclerView.Adapter<PembayaranAdapter.VH>() {

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
            holder.tv1.text = "${item.judul} (${item.status})"
            holder.tv2.text = "Rp ${item.nominal} • ${item.tanggal}  (Tap untuk Edit/Hapus)"
            holder.itemView.setOnClickListener { onClick(item) }
        }

        override fun getItemCount(): Int = items.size
    }
}