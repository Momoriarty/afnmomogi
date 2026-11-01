package com.example.afn_momogi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afn_momogi.databinding.ItemKlasemenBinding

class KlasemenAdapter(private val listKlasemen: List<KlasemenItem>) :
    RecyclerView.Adapter<KlasemenAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemKlasemenBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKlasemenBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listKlasemen[position]
        holder.binding.apply {
            tvPosisi.text = item.posisi.toString()
            tvNamaTim.text = item.namaTim
            tvMain.text = "${item.main} M"
            tvSG.text = item.selisihGol
            tvPoin.text = "${item.poin} P"
        }
    }

    override fun getItemCount(): Int = listKlasemen.size
}