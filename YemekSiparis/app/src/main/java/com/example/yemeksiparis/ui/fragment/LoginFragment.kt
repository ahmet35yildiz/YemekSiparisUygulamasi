package com.example.yemeksiparis.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.example.yemeksiparis.R
import com.example.yemeksiparis.databinding.FragmentLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class LoginFragment : Fragment() {
    private lateinit var tasarim: FragmentLoginBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tasarim = FragmentLoginBinding.inflate(inflater, container, false)

        return tasarim.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseAuth.getInstance().currentUser?.let {
            Navigation.findNavController(view).navigate(R.id.anasayfaGecis)
        }

        tasarim.buttonYeniUyelik.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.loginUyeOlGecis)
        }

        tasarim.buttonLogin.setOnClickListener {
            if (tasarim.textViewEmail.text.toString()
                    .isNotEmpty() && tasarim.textViewSifre.text.toString().isNotEmpty()
            ) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    tasarim.textViewEmail.text.toString(),
                    tasarim.textViewSifre.text.toString()
                )
                    .addOnCompleteListener { p0 ->
                        if (p0.isSuccessful) {
                            Snackbar.make(it, "Giriş Başarılı !", Snackbar.LENGTH_SHORT).show()

                            tasarim.animationView.setAnimation(R.raw.check)
                            tasarim.animationView.repeatCount = 0
                            tasarim.animationView.playAnimation()

                            val sayici = object : CountDownTimer(2000,1000){
                                override fun onTick(millisUntilFinished: Long) {}
                                override fun onFinish() {
                                    Navigation.findNavController(it).navigate(R.id.anasayfaGecis)
                                }
                            }
                            sayici.start()
                        } else {
                            Snackbar.make(it, "Giriş Başarısız !", Snackbar.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Snackbar.make(it, "Lütfen boş alanları doldurunuz !", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
