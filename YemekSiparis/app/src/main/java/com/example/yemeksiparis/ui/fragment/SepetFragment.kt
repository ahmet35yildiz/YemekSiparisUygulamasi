package com.example.yemeksiparis.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yemeksiparis.R
import com.example.yemeksiparis.ui.adapter.SepetYemeklerAdapter
import com.example.yemeksiparis.data.entity.SepetYemekler
import com.example.yemeksiparis.databinding.FragmentSepetBinding
import com.example.yemeksiparis.ui.viewmodel.SepetFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SepetFragment : Fragment() {
    private lateinit var tasarim:FragmentSepetBinding
    private lateinit var viewModel: SepetFragmentViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = DataBindingUtil.inflate(inflater,R.layout.fragment_sepet,container, false)
        tasarim.sepetFragment = this
        tasarim.sepetToolbarBaslik = "Sepetim"

        viewModel.sepetYemeklerListesi.observe(viewLifecycleOwner){
            val adapterSepet = SepetYemeklerAdapter(requireContext(),it,viewModel)
            tasarim.sepetYemeklerAdapter = adapterSepet


            var toplam = 0
            it.forEach{ yemek ->
                toplam += yemek.yemek_siparis_adet * yemek.yemek_fiyat
            }
            tasarim.textViewToplamTutar.text = (toplam - 10).toString() + " ₺"

            tasarim.buttonSepetiOnayla.setOnClickListener {
                Snackbar.make(it,"Siparişiniz tamamlanmıştır.",Snackbar.LENGTH_SHORT).show()
                tasarim.animationViewSepet.setAnimation(R.raw.check)
                tasarim.animationViewSepet.repeatCount = 0
                tasarim.animationViewSepet.playAnimation()

                val sayici = object : CountDownTimer(2000,1000){
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        Navigation.findNavController(it).navigate(R.id.sepetAnasayfaGecis)


                    }
                }
                sayici.start()

                buttonOnaylaTikla()
            }
        }
        return tasarim.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel:SepetFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    fun buttonOnaylaTikla () {
        viewModel.onayla()
    }

    override fun onResume() {
        super.onResume()
        Log.e("giriş", "${FirebaseAuth.getInstance().currentUser?.email}")
        viewModel.sepetYemekleriYukle(kullanici_adi = "${FirebaseAuth.getInstance().currentUser?.email}")
    }
}