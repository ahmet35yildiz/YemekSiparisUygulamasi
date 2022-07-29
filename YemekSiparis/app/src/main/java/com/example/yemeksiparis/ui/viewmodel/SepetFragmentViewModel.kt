package com.example.yemeksiparis.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yemeksiparis.data.entity.SepetYemekler
import com.example.yemeksiparis.data.repo.YemeklerDaoRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SepetFragmentViewModel @Inject constructor (var yrepo:YemeklerDaoRepository) : ViewModel() {
    var sepetYemeklerListesi = MutableLiveData<List<SepetYemekler>>()

    init {
        sepetYemekleriYukle(kullanici_adi = "${FirebaseAuth.getInstance().currentUser?.email}")
        sepetYemeklerListesi = yrepo.sepetYemekleriGetir()
    }

    fun onayla() {
        yrepo.onaylaTikla()
    }
    fun sil(sepet_yemek_id: Int,kullanici_adi: String) {
        yrepo.yemekSil(sepet_yemek_id, kullanici_adi)
    }
    fun sepetYemekleriYukle(kullanici_adi:String) {
        yrepo.tumSepetYemekleriAl(kullanici_adi)
    }
}