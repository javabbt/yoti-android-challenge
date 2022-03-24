package com.yoti.android.cryptocurrencychallenge.ui.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinCapRepository
import com.yoti.android.cryptocurrencychallenge.model.network.remote.MarketsApiData
import com.yoti.android.cryptocurrencychallenge.model.ui.AssetUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

typealias MarketDetailsLivedata = LiveData<MarketsApiData>

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val repository: CoinCapRepository
) : ViewModel() {

    fun getMarketDetails(
        coin: AssetUiItem
    ): MarketDetailsLivedata {
        return repository.getMarketDetails(coin)
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun getStates() = repository.marketStates

}