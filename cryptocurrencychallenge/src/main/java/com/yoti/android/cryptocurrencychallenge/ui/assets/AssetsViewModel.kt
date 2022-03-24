package com.yoti.android.cryptocurrencychallenge.ui.assets

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinCapRepository
import com.yoti.android.cryptocurrencychallenge.model.network.remote.AssetsApiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias AssetsLivedata = LiveData<AssetsApiData>


@HiltViewModel
class AssetsViewModel @Inject constructor(
    private val repository: CoinCapRepository
) : ViewModel() {

    fun getAssets(): AssetsLivedata {
        return repository.getAssets()
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun getAssetsFromLocal(): AssetsLivedata {
        return repository.getAssetsFromLocal()
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun getStates() = repository.states
}