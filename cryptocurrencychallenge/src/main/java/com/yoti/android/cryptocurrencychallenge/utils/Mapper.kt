package com.yoti.android.cryptocurrencychallenge.utils

import com.yoti.android.cryptocurrencychallenge.model.database.entity.CoinCapEntity
import com.yoti.android.cryptocurrencychallenge.model.network.remote.AssetData
import com.yoti.android.cryptocurrencychallenge.model.network.remote.AssetsApiData
import java.util.*

fun List<AssetData>.mapToCoinEntity(): List<CoinCapEntity> {
    return this.map {
        CoinCapEntity(
            changePercent24Hr = it.changePercent24Hr,
            explorer = it.explorer ,
            id = it.id!!,
            marketCapUsd = it.marketCapUsd ,
            maxSupply = it.maxSupply ,
            name = it.name ,
            priceUsd = it.priceUsd ,
            rank = it.rank ,
            supply = it.supply ,
            symbol = it.symbol ,
            volumeUsd24Hr = it.volumeUsd24Hr ,
            vwap24Hr = it.vwap24Hr
        )
    }
}

fun List<CoinCapEntity>.mapToCoinAssetData(): List<AssetData> {
    return this.map {
        AssetData(
            changePercent24Hr = it.changePercent24Hr,
            explorer = it.explorer ,
            id = it.id,
            marketCapUsd = it.marketCapUsd ,
            maxSupply = it.maxSupply ,
            name = it.name ,
            priceUsd = it.priceUsd ,
            rank = it.rank ,
            supply = it.supply ,
            symbol = it.symbol ,
            volumeUsd24Hr = it.volumeUsd24Hr ,
            vwap24Hr = it.vwap24Hr
        )
    }
}

fun List<CoinCapEntity>.mapToCoinAssetApiData(): AssetsApiData{
    return AssetsApiData(
        assetData = this.mapToCoinAssetData(),
        timestamp =  Date().time
    )
}
