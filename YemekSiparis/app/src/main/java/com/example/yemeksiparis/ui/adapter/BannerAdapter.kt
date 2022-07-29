package com.example.yemeksiparis.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yemeksiparis.data.entity.Banner
import com.example.yemeksiparis.databinding.CardTasarimBannerBinding

class BannerAdapter(var mContext: Context,var bannerListesi: List<Banner>) : RecyclerView.Adapter<BannerAdapter.CardTasarimTutucuBanner>(){
    inner class CardTasarimTutucuBanner(tasarim:CardTasarimBannerBinding) : RecyclerView.ViewHolder(tasarim.root) {
        var tasarim: CardTasarimBannerBinding
        init {
            this.tasarim = tasarim
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucuBanner {
        val layoutInflater = LayoutInflater.from(mContext)
        val tasarim = CardTasarimBannerBinding.inflate(layoutInflater,parent,false)
        return CardTasarimTutucuBanner(tasarim)
    }

    override fun onBindViewHolder(holder: CardTasarimTutucuBanner, position: Int) {
        val banner = bannerListesi.get(position)
        holder.tasarim.imageViewBanner.setImageResource(mContext.resources.getIdentifier(banner.banner_resim_adi,"drawable",mContext.packageName))

    }

    override fun getItemCount(): Int {
        return bannerListesi.size
    }
}