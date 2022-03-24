package com.yoti.android.cryptocurrencychallenge.crosscuting.connectivity
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

private class NetworkInfoManagerImpl(
    private val connectivityManager: ConnectivityManager,
) : NetworkInfoManager {

    override fun isConnectedToWiFi(): Boolean {
        var isWifi = false
        connectivityManager.allNetworks.forEach { network ->
            connectivityManager.getNetworkCapabilities(network).apply {
                if (this?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true) {
                    isWifi = true
                }
            }
        }
        return isWifi
    }

}

interface NetworkInfoManager {
    companion object {
        fun newInstance(
            connectivityManager: ConnectivityManager,
        ): NetworkInfoManager {
            return NetworkInfoManagerImpl(connectivityManager)
        }
    }

    fun isConnectedToWiFi(): Boolean
}