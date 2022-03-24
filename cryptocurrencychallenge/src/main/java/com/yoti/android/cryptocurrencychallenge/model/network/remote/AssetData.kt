package com.yoti.android.cryptocurrencychallenge.model.network.remote


import com.google.gson.annotations.SerializedName

data class AssetData(
    @SerializedName("changePercent24Hr")
    val changePercent24Hr: String?,
    @SerializedName("explorer")
    val explorer: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("marketCapUsd")
    val marketCapUsd: String?,
    @SerializedName("maxSupply")
    val maxSupply: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("priceUsd")
    val priceUsd: String?,
    @SerializedName("rank")
    val rank: String?,
    @SerializedName("supply")
    val supply: String?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("volumeUsd24Hr")
    val volumeUsd24Hr: String?,
    @SerializedName("vwap24Hr")
    val vwap24Hr: String?
)