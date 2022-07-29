package com.example.yemeksiparis.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.yemeksiparis.R
import com.example.yemeksiparis.data.entity.Yemekler
import com.example.yemeksiparis.databinding.CardTasarimAnasayfaBinding
import com.example.yemeksiparis.ui.fragment.AnasayfaFragmentDirections
import com.example.yemeksiparis.ui.viewmodel.AnasayfaFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class YemeklerAdapter(var mContext:Context,
                      var yemeklerListesi:List<Yemekler>,
                      var viewModel: AnasayfaFragmentViewModel) : RecyclerView.Adapter<YemeklerAdapter.CardTasarimTutucu>() {
    inner class CardTasarimTutucu(tasarim:CardTasarimAnasayfaBinding) : RecyclerView.ViewHolder(tasarim.root){
        var tasarim:CardTasarimAnasayfaBinding
        init {
            this.tasarim = tasarim
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val layoutInflater = LayoutInflater.from(mContext)
        val tasarim:CardTasarimAnasayfaBinding = DataBindingUtil.inflate(layoutInflater,R.layout.card_tasarim_anasayfa,parent,false)
        return CardTasarimTutucu(tasarim)
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val yemek = yemeklerListesi.get(position)
        val t = holder.tasarim
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
        Picasso.get().load(url).into(t.imageViewYemekResim)
        t.yemekNesnesi = yemek
        t.buttonSepeteEkle.setOnClickListener {
            val gecis = AnasayfaFragmentDirections.detayGecis(yemek = yemek)
            Navigation.findNavController(it).navigate(gecis)
        }
        t.imageViewFavEkle.setOnClickListener {
            Snackbar.make(it,"${yemek.yemek_adi} favorilere eklendi.",Snackbar.LENGTH_SHORT).show()
            viewModel.favEkle(yemek.yemek_id)
        }
        t.cardViewYemek.setOnClickListener {
            val gecis = AnasayfaFragmentDirections.detayGecis(yemek = yemek)
            Navigation.findNavController(it).navigate(gecis)
        }
    }

    override fun getItemCount(): Int {
        return yemeklerListesi.size
    }
}