package com.yoti.android.cryptocurrencychallenge.ui.assets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.yoti.android.cryptocurrencychallenge.R
import com.yoti.android.cryptocurrencychallenge.crosscuting.event.state.State
import com.yoti.android.cryptocurrencychallenge.databinding.FragmentAssetsBinding
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinCapRepository
import com.yoti.android.cryptocurrencychallenge.model.ui.AssetUiItem
import com.yoti.android.cryptocurrencychallenge.model.ui.dummyAssets
import com.yoti.android.cryptocurrencychallenge.ui.shared.extension.safeNavigate
import com.yoti.android.cryptocurrencychallenge.ui.shared.lifecycle.ViewLifecycleProperty
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "AssetsFragment"

@AndroidEntryPoint
class AssetsFragment : Fragment() {

    private var binding by ViewLifecycleProperty<FragmentAssetsBinding>()
    @Inject
    lateinit var coinCapRepository: CoinCapRepository
    private var assetsAdapter by ViewLifecycleProperty<AssetsAdapter>()
    private val viewModel by viewModels<AssetsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAssetsBinding.inflate(
            inflater,
            container,
            false
        ).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assetsAdapter = AssetsAdapter { coin ->
            val bundle = bundleOf("coin" to coin)
            findNavController(this).safeNavigate(
                R.id.action_assetsFragment_to_marketFragment,
                bundle
            )
        }
        viewModel.getAssets().observe(viewLifecycleOwner) { receivedData ->
            binding.recyclerViewAssets.apply {
                assetsAdapter.submitList(receivedData.assetData?.map {
                    AssetUiItem(
                        symbol = it.symbol!!,
                        name = it.name!!,
                        price = it.priceUsd!!,
                        baseId = it.id!!
                    )
                } ?: dummyAssets)
                this.adapter = assetsAdapter
                this.addItemDecoration(AssetsItemDecoration())
            }
        }

        viewModel.getStates().observe(viewLifecycleOwner) { event ->
            when (event.state) {
                State.InProgress -> binding.progressBarAssets.visibility = View.VISIBLE
                State.NotConnected -> {
                    binding.progressBarAssets.visibility = View.GONE
                    Snackbar.make(
                        binding.root,
                        R.string.no_internet_connection,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    fetchFromDb();
                }
                else -> binding.progressBarAssets.visibility = View.GONE
            }
        }


    }

    private fun fetchFromDb() {
        viewModel.getAssetsFromLocal().observe(viewLifecycleOwner){receivedData ->
            assetsAdapter.submitList(receivedData.assetData?.map {
                AssetUiItem(
                    symbol = it.symbol!!,
                    name = it.name!!,
                    price = it.priceUsd!!,
                    baseId = it.id!!
                )
            } ?: dummyAssets)
            binding.recyclerViewAssets.apply{
                this.adapter = assetsAdapter
                this.addItemDecoration(AssetsItemDecoration())
            }
        }
    }
}