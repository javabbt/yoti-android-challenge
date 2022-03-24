package com.yoti.android.cryptocurrencychallenge.ui.assets

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yoti.android.cryptocurrencychallenge.model.ui.AssetUiItem
import com.yoti.android.cryptocurrencychallenge.databinding.AssetItemBinding
import okhttp3.HttpUrl

class AssetsAdapter(private val onCoinClicked: (AssetUiItem) -> Unit) :
    ListAdapter<AssetUiItem, AssetsAdapter.AssetItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AssetItemViewHolder(
            AssetItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AssetItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffCallback : DiffUtil.ItemCallback<AssetUiItem>() {
        override fun areItemsTheSame(oldItem: AssetUiItem, newItem: AssetUiItem): Boolean {
            return oldItem.name == newItem.name
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: AssetUiItem, newItem: AssetUiItem): Boolean {
            return oldItem == newItem
        }
    }

    inner class AssetItemViewHolder(private val binding: AssetItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(asset: AssetUiItem) {
            binding.textViewAssetCode.text = asset.symbol
            binding.textViewAssetName.text = asset.name
            binding.textViewAssetPrice.text = asset.price
            itemView.setOnClickListener {
                onCoinClicked(asset)
            }
        }
    }

}


