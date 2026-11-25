package com.example.afn_momogi.network

import retrofit2.Call
import retrofit2.http.*

interface SupabaseService {
    // --- USERS (REGISTER) ---
    @POST("users")
    fun registerUser(@Body user: UserRequest): Call<List<UserRequest>>

    // --- CRUD TIM ---
    @GET("tim?order=id.desc")
    fun getTim(): Call<List<TimItem>>

    @POST("tim")
    fun createTim(@Body tim: TimItem): Call<List<TimItem>>

    @PATCH("tim")
    fun updateTim(
        @Query("id") idFilter: String, // format: "eq.1"
        @Body tim: Map<String, String>
    ): Call<List<TimItem>>

    @DELETE("tim")
    fun deleteTim(@Query("id") idFilter: String): Call<Void>

    // --- CRUD PEMBAYARAN ---
    @GET("pembayaran?order=id.desc")
    fun getPembayaran(): Call<List<PembayaranItem>>

    @POST("pembayaran")
    fun createPembayaran(@Body item: PembayaranItem): Call<List<PembayaranItem>>

    @PATCH("pembayaran")
    fun updatePembayaran(
        @Query("id") idFilter: String, // format: "eq.1"
        @Body data: Map<String, Any>
    ): Call<List<PembayaranItem>>

    @DELETE("pembayaran")
    fun deletePembayaran(@Query("id") idFilter: String): Call<Void>
}