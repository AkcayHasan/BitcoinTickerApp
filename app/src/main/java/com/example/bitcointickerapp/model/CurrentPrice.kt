package com.example.bitcointickerapp.model

import com.google.gson.annotations.SerializedName

data class CurrentPrice(
    @SerializedName("current_price")
    var currentPrice : CurrentPriceType
)
