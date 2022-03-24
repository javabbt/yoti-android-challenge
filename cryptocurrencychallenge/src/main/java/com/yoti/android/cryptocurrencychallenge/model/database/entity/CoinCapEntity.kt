package com.yoti.android.cryptocurrencychallenge.model.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coin")
data class CoinCapEntity(
    @ColumnInfo(name = "changePercent24Hr")
    val changePercent24Hr: String?,
    @ColumnInfo(name = "explorer")
    val explorer: String?,
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "marketCapUsd")
    val marketCapUsd: String?,
    @ColumnInfo(name = "maxSupply")
    val maxSupply: String?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "priceUsd")
    val priceUsd: String?,
    @ColumnInfo(name = "rank")
    val rank: String?,
    @ColumnInfo(name = "supply")
    val supply: String?,
    @ColumnInfo(name = "symbol")
    val symbol: String?,
    @ColumnInfo(name = "volumeUsd24Hr")
    val volumeUsd24Hr: String?,
    @ColumnInfo(name = "vwap24Hr")
    val vwap24Hr: String?

)