package com.yoti.android.cryptocurrencychallenge.model.network.api

import com.yoti.android.cryptocurrencychallenge.model.network.remote.ApiResponse
import com.yoti.android.cryptocurrencychallenge.model.network.remote.AssetsApiData
import com.yoti.android.cryptocurrencychallenge.model.network.remote.MarketsApiData
import retrofit2.http.GET
import retrofit2.http.Path

const val CAPCOIN_ENDPOINT_HOST = "https://api.coincap.io/"

interface CoincapService {

    @GET("/v2/assets")
    suspend fun getAssets(): ApiResponse<AssetsApiData>

    @GET("/v2/markets")
    suspend fun getMarkets(
    ): ApiResponse<MarketsApiData>
}