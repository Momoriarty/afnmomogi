package com.example.afn_momogi.network

data class UserRequest(
    val id: Long? = null,
    val nama: String,
    val email: String,
    val tanggal_lahir: String,
    val jenis_kelamin: String,
    val username: String,
    val password: String
)

data class TimItem(
    val id: Long? = null,
    val nama_tim: String,
    val nama_kapten: String,
    val no_wa: String,
    val turnamen: String? = "Melayu Champions League"
)

data class PembayaranItem(
    val id: Long? = null,
    val judul: String,
    val nominal: Int,
    val status: String = "Pending",
    val tanggal: String = "25 Nov 2026"
)