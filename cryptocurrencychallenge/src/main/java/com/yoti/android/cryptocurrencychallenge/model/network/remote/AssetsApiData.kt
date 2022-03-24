package com.yoti.android.cryptocurrencychallenge.model.network.remote


import com.google.gson.annotations.SerializedName
import com.yoti.android.cryptocurrencychallenge.model.network.remote.AssetData
data class AssetsApiData(
    @SerializedName("data")
        val assetData: List<AssetData>?,
    @SerializedName("timestamp")
        val timestamp: Long?
)