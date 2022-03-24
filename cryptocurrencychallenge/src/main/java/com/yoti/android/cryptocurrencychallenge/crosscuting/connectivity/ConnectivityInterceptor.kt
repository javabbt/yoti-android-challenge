package com.yoti.android.cryptocurrencychallenge.crosscuting.connectivity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.google.gson.JsonObject
import com.yoti.android.cryptocurrencychallenge.R
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

private const val TAG = "ConnectivityInterceptor"
class ConnectivityInterceptor(
    private val context: Context,
) : Interceptor {

    companion object {
        private const val HEADER_KEY_X_SOURCE = "X-Source"
        const val HTTP_CLIENT_CLOSED_REQUEST_CODE = 499
        private const val HTTP_CLIENT_CLOSED_REQUEST_MESSAGE = "Client Closed Request"
    }

    private val connectivityManager: ConnectivityManager
        get() = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (hasInternet()) chain.proceed(chain.request())
        else createNotConnectedResponse(chain.request())
    }

    private fun hasInternet(): Boolean =
        connectivityManager.run {
            if (allNetworks.isEmpty()) false
            else this.allNetworks.mapNotNull {
                getNetworkCapabilities(it)?.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_INTERNET
                )
            }.reduce { acc, b -> acc || b }
        }


    private fun createNotConnectedResponse(request: Request): Response {
        val response =  Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(HTTP_CLIENT_CLOSED_REQUEST_CODE)
            .message(HTTP_CLIENT_CLOSED_REQUEST_MESSAGE)
            .addHeader(HEADER_KEY_X_SOURCE, "application")
            .addHeader("Content-Encoding", "gzip")
            .addHeader("Content-Type", "application/json")
            .body(JsonObject().apply {
                this.addProperty(
                    "message",
                    context.getString(R.string.shared_error_no_connection)
                )
            }.toString().toResponseBody())
            .build()
        Log.d(TAG, "createNotConnectedResponse: created response $response")
        return response
    }
}