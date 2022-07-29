package com.example.yemeksiparis.ui.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.yemeksiparis.databinding.FragmentYemekDetayBinding
import com.example.yemeksiparis.ui.viewmodel.YemekDetayFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YemekDetayFragment : Fragment() {
    private lateinit var tasarim:FragmentYemekDetayBinding
    private lateinit var viewModel: YemekDetayFragmentViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentYemekDetayBinding.inflate(inflater, container, false)

        val bundle:YemekDetayFragmentArgs by navArgs()
        val gelenYemek = bundle.yemek


        tasarim.textViewSiparisAdedi.text = "1"
        var alinanSiparisAdedi = tasarim.textViewSiparisAdedi.text.toString()
        var yemek_siparis_adet = alinanSiparisAdedi.toInt()

        if (alinanSiparisAdedi == "1"){
            val alphaAnimation = ObjectAnimator.ofFloat(tasarim.buttonAdetAzalt,"alpha",1.0f,0.5f).apply {
                duration = 0
            }
            alphaAnimation.start()
        }

        tasarim.toolbarYemekDetay.title = gelenYemek.yemek_adi
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${gelenYemek.yemek_resim_adi}"
        Picasso.get().load(url).into(tasarim.ivYemekResim)
        tasarim.tvYemekAdi.text = gelenYemek.yemek_adi
        tasarim.tvYemekFiyat.text = "₺ ${gelenYemek.yemek_fiyat}"
        tasarim.btnSepeteEkle.setOnClickListener {
            Snackbar.make(it,"${yemek_siparis_adet} adet ${gelenYemek.yemek_adi} sepete eklendi.",Snackbar.LENGTH_SHORT).show()
            btnSepeteEkleTikla(gelenYemek.yemek_adi,gelenYemek.yemek_resim_adi,gelenYemek.yemek_fiyat,yemek_siparis_adet,"${FirebaseAuth.getInstance().currentUser?.email}")
        }

        tasarim.buttonAdetArttir.setOnClickListener {
            yemek_siparis_adet += 1
            tasarim.textViewSiparisAdedi.text = yemek_siparis_adet.toString()
            val alphaAnimation = ObjectAnimator.ofFloat(tasarim.buttonAdetAzalt,"alpha",0.5f,1.0f).apply {
                duration = 0
            }
            alphaAnimation.start()


        }
        tasarim.buttonAdetAzalt.setOnClickListener {
            yemek_siparis_adet -= 1
            tasarim.textViewSiparisAdedi.text = yemek_siparis_adet.toString()
            if (yemek_siparis_adet == 0){
                yemek_siparis_adet = 1
                tasarim.textViewSiparisAdedi.text = yemek_siparis_adet.toString()
                Snackbar.make(it,"En az 1 adet ${gelenYemek.yemek_adi} siparişi verebilirsiniz.",Snackbar.LENGTH_SHORT).show()
            }
            if (yemek_siparis_adet == 1){
                val alphaAnimation = ObjectAnimator.ofFloat(tasarim.buttonAdetAzalt,"alpha",1.0f,0.5f).apply {
                    duration = 0
                }
                alphaAnimation.start()
            }
        }

        return tasarim.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel:YemekDetayFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    fun btnSepeteEkleTikla (yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int, yemek_siparis_adet: Int, kullanici_adi: String){
        viewModel.sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }
}

