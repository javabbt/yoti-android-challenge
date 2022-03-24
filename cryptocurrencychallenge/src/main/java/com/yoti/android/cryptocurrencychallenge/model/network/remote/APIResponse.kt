package com.yoti.android.cryptocurrencychallenge.model.network.remote

import com.google.gson.JsonParser
import com.google.gson.annotations.SerializedName
import okhttp3.HttpUrl
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

typealias ApiResponse<T> = Response<T>


data class ResponseStructure<T>(
    @SerializedName("data") val data: T,
    @SerializedName("timestamp") val timeStamp: Long? = null,
)

fun <T> Response<T>.unwrapError(): String? {
    if (this.isSuccessful) throw IllegalStateException("Can not parse a successful response")
    val errorBody = errorBody()?.string()?.let { JsonParser().parse(it).asJsonObject }
    return errorBody?.get("message")?.asString
}

@Throws(IllegalStateException::class)
inline fun <reified T> Response<T>.safeUnwrap(
    successCode: Int? = null,
): T? {
    val predicate = successCode?.equals(this.code()) ?: this.isSuccessful
    if (predicate) {
        return this.body()
    } else {
        throw IllegalStateException(successCode?.run {
            "Response is not a success, $successCode expected but was ${code()}"
        } ?: run {
            "Response is not a success, code was ${this@safeUnwrap.code()}"
        })
    }
}

class NoContentException : IllegalStateException("No content from api")



