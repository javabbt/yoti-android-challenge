package com.yoti.android.cryptocurrencychallenge.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yoti.android.cryptocurrencychallenge.crosscuting.connectivity.ConnectivityInterceptor.Companion.HTTP_CLIENT_CLOSED_REQUEST_CODE
import com.yoti.android.cryptocurrencychallenge.crosscuting.coroutine.DispatcherProvider
import com.yoti.android.cryptocurrencychallenge.crosscuting.event.state.EventState
import com.yoti.android.cryptocurrencychallenge.crosscuting.event.state.EventState.Companion.updateState
import com.yoti.android.cryptocurrencychallenge.crosscuting.event.state.State
import com.yoti.android.cryptocurrencychallenge.model.database.dao.CoinCapDao
import com.yoti.android.cryptocurrencychallenge.model.database.entity.CoinCapEntity
import com.yoti.android.cryptocurrencychallenge.model.network.api.CoincapService
import com.yoti.android.cryptocurrencychallenge.model.network.remote.*
import com.yoti.android.cryptocurrencychallenge.model.ui.AssetUiItem
import com.yoti.android.cryptocurrencychallenge.utils.mapToCoinAssetApiData
import com.yoti.android.cryptocurrencychallenge.utils.mapToCoinAssetData
import com.yoti.android.cryptocurrencychallenge.utils.mapToCoinEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class CoinCapRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val coincapService: CoincapService,
    private val coinCapDao: CoinCapDao,
) : CoinCapRepository {

    companion object {
        private const val TAG: String = "CoinCapRepository"
    }

    private val _states = MutableLiveData<EventState>()
    override val states: LiveData<EventState>
        get() = _states

    private val _marketStates = MutableLiveData<EventState>()
    override val marketStates: LiveData<EventState>
        get() = _marketStates

    override fun getAssets(): Flow<AssetsApiData> {
        return flow {
            _states updateState State.InProgress
            this.getAssetsFromApi()
        }.flowOn(dispatcherProvider.io())
            .mapLatest { it ->
                it.assetData?.mapToCoinEntity()
                    .also { coinCapDao.insertCoins(it!!) }
                it
            }.flowOn(dispatcherProvider.default())
    }

    override fun getAssetsFromLocal(): Flow<AssetsApiData> {
        Log.d(TAG, "getAssetsFromLocal: nini")
       return channelFlow {
           _marketStates updateState State.NotConnected
           coinCapDao.getCoinsFromLocal().collectLatest () {
               Log.d(TAG, "getAssetsFromLocal: local list $it")
               val localList = it.mapToCoinAssetApiData()
               send(localList)
           }
       }
    }


    override fun getMarketDetails(coin: AssetUiItem): Flow<MarketsApiData> {
        return flow {
            _marketStates updateState State.InProgress
            this.getMarketDetails(coin)
        }.flowOn(dispatcherProvider.io())
            .mapLatest { value: MarketsApiData ->
                val newList = value.marketData?.filter { data ->
                    data.baseId == coin.baseId
                }?.sortedBy { it.priceQuote }
                value.marketData = newList
                value
            }
            .flowOn(dispatcherProvider.default())
    }

    private suspend fun FlowCollector<MarketsApiData>.getMarketDetails(coin: AssetUiItem) {
        val response = try {
            coincapService.getMarkets()
        } catch (t: Throwable) {
            _marketStates updateState State.Failure
            return
        }
        Log.d(TAG, "getMarketDetailsFromApi: response is $response")
        try {
            when {
                response.isSuccessful -> {
                    emit(
                        response.safeUnwrap()
                            ?: throw IllegalStateException("null")
                    )
                    _marketStates updateState State.Success
                }
                response.code() == HTTP_CLIENT_CLOSED_REQUEST_CODE ->{
                    _marketStates updateState State.NotConnected
                }
                else -> throw IllegalStateException()
            }
        } catch (e: Exception) {
            _marketStates.postValue(EventState(State.Failure, response.unwrapError()))
        }
    }

    private suspend fun FlowCollector<AssetsApiData>.getAssetsFromApi(
    ) {

        val response = try {
            coincapService.getAssets()
        } catch (t: Throwable) {
            _states updateState State.Failure
            null
        }
        Log.d(TAG, "getAssetsFromApi: response is $response")
        try {
            when {
                response == null -> {
                    _states updateState State.NotConnected
                }
                response.isSuccessful -> {
                    Log.d(TAG, "getAssetsFromApi: dd")
                    emit(
                        response.safeUnwrap()
                            ?: throw IllegalStateException("null")
                    )
                    _states updateState State.Success
                }
                response.code() == HTTP_CLIENT_CLOSED_REQUEST_CODE -> {
                    Log.d(TAG, "getAssetsFromApi: no conn")
                    _states updateState State.NotConnected
                }
                else -> throw IllegalStateException()
            }
        } catch (e: Exception) {
            _states.postValue( EventState(State.Failure, response?.unwrapError()))
        }
    }

}


interface CoinCapRepository {

    companion object {
        fun createInstance(
            dispatcherProvider: DispatcherProvider,
            coincapService: CoincapService,
            coinCapDao: CoinCapDao,
        ): CoinCapRepository {
            return CoinCapRepositoryImpl(dispatcherProvider, coincapService, coinCapDao)
        }
    }

    val states: LiveData<EventState>
    val marketStates: LiveData<EventState>
    fun getAssets(): Flow<AssetsApiData>
    fun getAssetsFromLocal(): Flow<AssetsApiData>
    fun getMarketDetails(coin: AssetUiItem): Flow<MarketsApiData>
}
