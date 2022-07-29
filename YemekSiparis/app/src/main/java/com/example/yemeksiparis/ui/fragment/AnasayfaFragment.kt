package com.example.yemeksiparis.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yemeksiparis.R
import com.example.yemeksiparis.data.entity.Banner
import com.example.yemeksiparis.data.entity.SlowlyLinearLayoutManager
import com.example.yemeksiparis.databinding.FragmentAnasayfaBinding
import com.example.yemeksiparis.ui.adapter.BannerAdapter
import com.example.yemeksiparis.ui.adapter.YemeklerAdapter
import com.example.yemeksiparis.ui.viewmodel.AnasayfaFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnasayfaFragment : Fragment(),SearchView.OnQueryTextListener {
    private lateinit var tasarim:FragmentAnasayfaBinding
    private lateinit var viewModel: AnasayfaFragmentViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        tasarim = FragmentAnasayfaBinding.inflate(inflater,container,false)
        tasarim = DataBindingUtil.inflate(inflater,R.layout.fragment_anasayfa  ,container, false)
        tasarim.anasayfaFragment = this
        tasarim.anasayfaToolbarBaslik = "Yemekler"
        (activity as AppCompatActivity).setSupportActionBar(tasarim.toolbarAnasayfa)

        viewModel.yemeklerListesi.observe(viewLifecycleOwner){
            val adapter = YemeklerAdapter(requireContext(),it,viewModel)
            tasarim.yemeklerAdapter = adapter
        }

        val bannerListesi = ArrayList<Banner>()
        val b1 = Banner(1,"banner1")
        val b2 = Banner(2,"banner2")
        val b3 = Banner(3,"banner3")
        bannerListesi.add(b1)
        bannerListesi.add(b2)
        bannerListesi.add(b3)
        val adapterBanner = BannerAdapter(requireContext(),bannerListesi)
        tasarim.rvBanner.adapter = adapterBanner
        val timer = object :CountDownTimer(5500,1000){
            override fun onTick(millisUntilFinished: Long) {
                tasarim.rvBanner.post {
                    tasarim.rvBanner.smoothScrollToPosition(adapterBanner.itemCount+3)
                }
            }

            override fun onFinish() {
                tasarim.rvBanner.post {
                    tasarim.rvBanner.smoothScrollToPosition(0)
                }
            }
        }
        object : CountDownTimer(1000,1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                timer.start()
            }
        }.start()
        tasarim.rvBanner.layoutManager = SlowlyLinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        return tasarim.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val tempViewModel:AnasayfaFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu,menu)
        val item = menu.findItem(R.id.action_ara)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cikis -> {
                FirebaseAuth.getInstance().signOut()
                Navigation.findNavController(requireView()).navigate(R.id.anasayfaLoginGecis)
                Snackbar.make(requireView(), "Çıkış Başarılı !", Snackbar.LENGTH_SHORT).show()

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.ara(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        viewModel.ara(newText)
        return true
    }
}