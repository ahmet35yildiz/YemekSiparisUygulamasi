package com.example.yemeksiparis.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.yemeksiparis.R
import com.example.yemeksiparis.databinding.FragmentUyeOlBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class UyeOlFragment : Fragment() {
    private lateinit var tasarim:FragmentUyeOlBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentUyeOlBinding.inflate(inflater, container, false)
        return tasarim.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseAuth.getInstance().currentUser?.let {
            Navigation.findNavController(view).navigate(R.id.uyeOlAnasayfaGecis)
        }

        tasarim.buttonZatenUyeyim.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.loginGecis)
        }

        tasarim.buttonUyeOl.setOnClickListener {
            if (tasarim.textViewEmailUyeOl.text.toString()
                    .isNotEmpty() && tasarim.textViewSifreUyeOl.text.toString().isNotEmpty()
            ) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    tasarim.textViewEmailUyeOl.text.toString(),
                    tasarim.textViewSifreUyeOl.text.toString()
                ).addOnCompleteListener (object :OnCompleteListener<AuthResult>{
                    override fun onComplete(p0: Task<AuthResult>) {
                        if (p0.isSuccessful){
                            Snackbar.make(it, "Üyelik başarıyla oluşturuldu !", Snackbar.LENGTH_SHORT).show()
                            FirebaseAuth.getInstance().signOut()

                            tasarim.animationViewUyeOl.setAnimation(R.raw.check)
                            tasarim.animationViewUyeOl.repeatCount = 0
                            tasarim.animationViewUyeOl.playAnimation()

                            val sayici = object : CountDownTimer(2000,1000){
                                override fun onTick(millisUntilFinished: Long) {}
                                override fun onFinish() {
                                    Navigation.findNavController(it).navigate(R.id.loginGecis)
                                }
                            }
                            sayici.start()
                        }else{
                            Snackbar.make(it, "Üyelik oluşturulamadı !", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                })
            } else {
                Snackbar.make(it, "Lütfen boş alanları doldurunuz !", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}