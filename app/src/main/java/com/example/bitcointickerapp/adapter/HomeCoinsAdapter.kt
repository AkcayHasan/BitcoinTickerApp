package com.example.bitcointickerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcointickerapp.R
import com.example.bitcointickerapp.databinding.ItemCoinsBinding
import com.example.bitcointickerapp.model.CryptoModel
import com.example.bitcointickerapp.view.fragments.HomeFragmentDirections

class HomeCoinsAdapter : RecyclerView.Adapter<HomeCoinsAdapter.CoinsViewHolder>() {

    private var coinsList = arrayListOf<CryptoModel>()

    inner class CoinsViewHolder(val view : ItemCoinsBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCoinsBinding>(inflater,R.layout.item_coins,parent,false)
        return CoinsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {

        holder.view.viewModel = coinsList[position]

        holder.itemView.setOnClickListener {
            val action = coinsList[position].id?.let { coinId ->
                HomeFragmentDirections.homeFragmentToDetailFragment(
                    coinId,
                    false
                )
            }
            if (action != null) {
                it.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int = coinsList.size

    fun setData(itemList : List<CryptoModel>){
        coinsList.clear()
        coinsList.addAll(itemList)
        notifyDataSetChanged()
    }


}