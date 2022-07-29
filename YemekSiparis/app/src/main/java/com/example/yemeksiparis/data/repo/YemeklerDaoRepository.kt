package com.example.yemeksiparis.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.yemeksiparis.data.entity.*
import com.example.yemeksiparis.retrofit.YemeklerDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YemeklerDaoRepository(var ydao:YemeklerDao) {
    var yemeklerListesi:MutableLiveData<List<Yemekler>>
    var sepetYemeklerListesi:MutableLiveData<List<SepetYemekler>>

    init {
        yemeklerListesi = MutableLiveData()
        sepetYemeklerListesi = MutableLiveData()
    }

    fun yemekleriGetir() : MutableLiveData<List<Yemekler>> {
        return yemeklerListesi
    }

    fun sepetYemekleriGetir() : MutableLiveData<List<SepetYemekler>>{
        return sepetYemeklerListesi
    }

    fun yemekSepeteEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int, yemek_siparis_adet: Int, kullanici_adi: String) {
        ydao.sepetYemekEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi).enqueue(object : Callback<CRUDCevap>{
            override fun onResponse(call: Call<CRUDCevap>?, response: Response<CRUDCevap>) {
                val basari = response.body().success
                val mesaj = response.body().message
                Log.e("Yemek kayıt","$basari - $mesaj")
            }

            override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {}
        })
    }

    fun yemekAra(aramaKelimesi: String) {
        Log.e("Yemek ara", aramaKelimesi)
    }

    fun onaylaTikla() {
        Log.e("Yemek sipariş", "Sipariş onaylandı.")

    }

    fun yemekFavEkle(yemek_id:Int){
        Log.e("Yemek favorilere ekle","${yemek_id} id numaralı yemek favorilere eklendi.")
    }

    fun yemekSil(sepet_yemek_id: Int,kullanici_adi: String) {
        ydao.sepettenSil(sepet_yemek_id,kullanici_adi).enqueue(object : Callback<CRUDCevap>{
            override fun onResponse(call: Call<CRUDCevap>?, response: Response<CRUDCevap>) {
                val basari = response.body().success
                val mesaj = response.body().message
                Log.e("Sepet Yemek sil","$basari - $mesaj")
                tumSepetYemekleriAl(kullanici_adi)
            }

            override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {}
        })
    }

    fun tumYemekleriAl() {
        ydao.tumYemekler().enqueue(object: Callback<YemeklerCevap> {
            override fun onResponse(call: Call<YemeklerCevap>?, response: Response<YemeklerCevap>) {
                val liste = response.body().yemekler
                yemeklerListesi.value = liste
            }

            override fun onFailure(call: Call<YemeklerCevap>?, t: Throwable?) {}
        })
    }

    fun tumSepetYemekleriAl(kullanici_adi: String) {
        ydao.sepetGoster(kullanici_adi).enqueue(object: Callback<SepetYemeklerCevap> {
            override fun onResponse(call: Call<SepetYemeklerCevap>?, response: Response<SepetYemeklerCevap>) {
                val sepetListe = response.body().sepet_yemekler
                sepetYemeklerListesi.value = sepetListe
            }

            override fun onFailure(call: Call<SepetYemeklerCevap>?, t: Throwable?) {}
        })
    }
}