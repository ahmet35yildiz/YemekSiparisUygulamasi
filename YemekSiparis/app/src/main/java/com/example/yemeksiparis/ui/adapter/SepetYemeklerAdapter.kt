package com.example.yemeksiparis.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yemeksiparis.data.entity.SepetYemekler
import com.example.yemeksiparis.databinding.CardTasarimSepetBinding
import com.example.yemeksiparis.ui.viewmodel.SepetFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class SepetYemeklerAdapter(var mContext:Context,
                           var sepetYemeklerListesi:List<SepetYemekler>,
                           var viewModel: SepetFragmentViewModel)
    : RecyclerView.Adapter<SepetYemeklerAdapter.CardTasarimTutucuSepet>() {

    inner class CardTasarimTutucuSepet(tasarim:CardTasarimSepetBinding) : RecyclerView.ViewHolder(tasarim.root){
        var tasarim:CardTasarimSepetBinding
        init {
            this.tasarim = tasarim
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucuSepet {
        val layoutInflater = LayoutInflater.from(mContext)
        val tasarim = CardTasarimSepetBinding.inflate(layoutInflater, parent, false)
        return CardTasarimTutucuSepet(tasarim)
    }

    override fun onBindViewHolder(holder: CardTasarimTutucuSepet, position: Int) {
        val sepetYemek = sepetYemeklerListesi.get(position)
        val t = holder.tasarim

        var alinanSiparisAdedi = t.textViewYemekAdetSepet.text.toString()
        var yemek_siparis_adet = alinanSiparisAdedi.toInt()

        t.textViewYemekAdiSepet.text = "${sepetYemek.yemek_adi}"
        t.textViewYemekAdetSepet.text = "${sepetYemek.yemek_siparis_adet} adet"
        t.textViewYemekFiyatSepet.text = "₺ ${(sepetYemek.yemek_siparis_adet * sepetYemek.yemek_fiyat)}"
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${sepetYemek.yemek_resim_adi}"
        Picasso.get().load(url).into(t.imageViewYemekResimSepet)
        t.imageViewSepettenSil.setOnClickListener {
            Snackbar.make(it,"${sepetYemek.yemek_adi} sepetten çıkarılsın mı?", Snackbar.LENGTH_LONG).setAction("EVET"){
                Snackbar.make(it,"${sepetYemek.yemek_adi} sepetten çıkarıldı.",Snackbar.LENGTH_LONG).show()
                viewModel.sil(sepetYemek.sepet_yemek_id, kullanici_adi="${FirebaseAuth.getInstance().currentUser?.email}")
            }.show()
        }
        t.cardViewSepet.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return sepetYemeklerListesi.size
    }
}