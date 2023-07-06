package com.example.tod0.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.tod0.R
import com.example.tod0.databinding.FragmentSignInBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


class SignInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater , container : ViewGroup?,
    savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignInBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view , savedInstanceState)

        init(view)
        registerEvents()
    }

    private fun init(view: View){
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }

    private fun registerEvents() {

        binding.authTextView.setOnClickListener {
            navControl.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.nextBtn.setOnClickListener {
            val email = binding.emailET.text.toString().trim()
            val pass = binding.passET.text.toString().trim()


            if (email.isNotEmpty() && pass.isNotEmpty()) {

                    auth.signInWithEmailAndPassword(email , pass).addOnCompleteListener(
                        OnCompleteListener {
                            if (it.isSuccessful){

                                navControl.navigate( R.id.action_signInFragment_to_homeFragment)
                            }else{
                                Toast.makeText(context , it.exception?.message , Toast.LENGTH_SHORT).show()
                            }
                        })

            }
        }
    }

}