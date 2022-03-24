package com.yoti.android.cryptocurrencychallenge.crosscuting.coroutine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

private class AddictiveDispatcherProvider : DispatcherProvider

interface DispatcherProvider {

    companion object {
        fun createInstance(): DispatcherProvider = AddictiveDispatcherProvider()
    }

    fun main(): CoroutineDispatcher = Dispatchers.Main

    fun io(): CoroutineDispatcher = Dispatchers.IO

    fun default(): CoroutineDispatcher = Dispatchers.Default

    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined

}

