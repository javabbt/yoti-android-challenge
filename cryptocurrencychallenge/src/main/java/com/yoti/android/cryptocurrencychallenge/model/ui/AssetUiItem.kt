package com.yoti.android.cryptocurrencychallenge.model.ui

import java.io.Serializable

data class AssetUiItem(val symbol: String, val name: String, val price: String , var baseId:String) : Serializable
