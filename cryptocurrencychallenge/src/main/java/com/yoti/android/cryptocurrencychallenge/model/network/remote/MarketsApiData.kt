package com.yoti.android.cryptocurrencychallenge.model.network.remote


import com.google.gson.annotations.SerializedName
import com.yoti.android.cryptocurrencychallenge.model.network.remote.MarketData

data class MarketsApiData(
    @SerializedName("data")
    var marketData: List<MarketData>?,
    @SerializedName("timestamp")
        val timestamp: Long?
)