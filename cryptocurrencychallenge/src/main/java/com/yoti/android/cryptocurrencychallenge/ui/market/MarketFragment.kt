package com.yoti.android.cryptocurrencychallenge.ui.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.yoti.android.cryptocurrencychallenge.R
import com.yoti.android.cryptocurrencychallenge.crosscuting.event.state.State
import com.yoti.android.cryptocurrencychallenge.databinding.FragmentMarketBinding
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinCapRepository
import com.yoti.android.cryptocurrencychallenge.model.ui.AssetUiItem
import com.yoti.android.cryptocurrencychallenge.ui.shared.lifecycle.ViewLifecycleProperty
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val TAG = "MarketFragment"

@AndroidEntryPoint
class MarketFragment : Fragment() {

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE)

    private var binding by ViewLifecycleProperty<FragmentMarketBinding>()
    @Inject
    lateinit var coinCapRepository: CoinCapRepository
    private val viewModel by viewModels<MarketViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return FragmentMarketBinding.inflate(
            inflater,
            container,
            false
        ).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coin = arguments?.get("coin") as AssetUiItem

        viewModel.getMarketDetails(coin).observe(viewLifecycleOwner){ apiData ->
            binding.apply {
                //null exception when list empty
                if(!apiData.marketData.isNullOrEmpty()) {
                    val coinMarket = apiData.marketData?.get(0)
                    textViewExchangeId.text = coinMarket?.exchangeId
                    textViewRank.text = coinMarket?.rank
                    textViewPrice.text = coinMarket?.priceUsd
                    textViewDate.text = getDate(coinMarket?.updated ?: 0)
                }else{
                    Snackbar.make(
                        binding.root,
                        R.string.market_details,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.getStates().observe(viewLifecycleOwner) { event ->
            when (event.state) {
                State.InProgress -> binding.progressBarMarket.visibility = View.VISIBLE
                State.NotConnected -> {
                    binding.progressBarMarket.visibility = View.GONE
                    Snackbar.make(
                        binding.root,
                        R.string.no_internet_connection,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                else -> binding.progressBarMarket.visibility = View.GONE
            }
        }

    }

    private fun getDate(milliSeconds: Long): String? {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return dateFormat.format(calendar.time)
    }
}