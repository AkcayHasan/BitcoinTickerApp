package com.example.bitcointickerapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.bitcointickerapp.databinding.ActivityLoginBinding
import com.example.bitcointickerapp.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var viewModel : LoginViewModel

    private var userEmailAddress = ""
    private var userPassword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        actions()

    }

    private fun actions(){
        binding.btnLogin.setOnClickListener {
            userEmailAddress = binding.etEmailAddress.text.toString()
            userPassword = binding.etPassword.text.toString()
            //viewModel.signInFirebase(this,userEmailAddress)
            viewModel.signInWithEmailAndPassword(this, userEmailAddress, userPassword)
        }

        binding.btnRegister.setOnClickListener {
            userEmailAddress = binding.etEmailAddress.text.toString()
            userPassword = binding.etPassword.text.toString()
            //viewModel.signInFirebase(this,userEmailAddress)
            viewModel.registerWithEmailAndPassword(this, userEmailAddress, userPassword)
        }
    }

}