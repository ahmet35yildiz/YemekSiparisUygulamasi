package com.example.yemeksiparis.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yemeksiparis.data.entity.Yemekler
import com.example.yemeksiparis.data.repo.YemeklerDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnasayfaFragmentViewModel @Inject constructor (var yrepo:YemeklerDaoRepository) : ViewModel() {

    var yemeklerListesi = MutableLiveData<List<Yemekler>>()

    init {
        yemekleriYukle()
        yemeklerListesi = yrepo.yemekleriGetir()
    }

    fun ara(aramaKelimesi:String){
        yrepo.yemekAra(aramaKelimesi)
    }

    fun favEkle(yemek_id:Int){
        yrepo.yemekFavEkle(yemek_id)
    }

    fun yemekleriYukle() {
        yrepo.tumYemekleriAl()
    }
}