package com.yoti.android.cryptocurrencychallenge.model.network.remote


import com.google.gson.annotations.SerializedName

data class MarketData(
    @SerializedName("baseId")
    val baseId: String?,
    @SerializedName("baseSymbol")
    val baseSymbol: String?,
    @SerializedName("exchangeId")
    val exchangeId: String?,
    @SerializedName("percentExchangeVolume")
    val percentExchangeVolume: Any?,
    @SerializedName("priceQuote")
    val priceQuote: String?,
    @SerializedName("priceUsd")
    val priceUsd: String?,
    @SerializedName("quoteId")
    val quoteId: String?,
    @SerializedName("quoteSymbol")
    val quoteSymbol: String?,
    @SerializedName("rank")
    val rank: String?,
    @SerializedName("tradesCount24Hr")
    val tradesCount24Hr: Any?,
    @SerializedName("updated")
    val updated: Long?,
    @SerializedName("volumeUsd24Hr")
    val volumeUsd24Hr: String?
)