package com.example.bitcointickerapp.network

import com.example.bitcointickerapp.model.CryptoModel
import com.example.bitcointickerapp.model.CryptoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //https://api.coingecko.com/api/v3/coins/markets

    @GET("coins/markets")
    fun getAllData(
        @Query("vs_currency") currencyType : String,
    ) : Single<List<CryptoModel>>

    @GET("coins/{id}")
    fun getData(
        @Path("id") id : String
    ) : Single<CryptoResponse>


}