package com.example.bitcointickerapp.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.bitcointickerapp.view.MainActivity
import com.example.bitcointickerapp.view.fragments.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings

class LoginViewModel : ViewModel() {

    private lateinit var auth: FirebaseAuth

    fun signInFirebase(context: Context, email: String) {
        auth = FirebaseAuth.getInstance()

        val intent = Intent()
        val emailLink = intent.data.toString()

        if (emailLink != "null") {
            val actionCodeSettings = actionCodeSettings {
                url = "https://bitcointickerapp.page.link"
                handleCodeInApp = true
                setIOSBundleId("com.example.ios")
                setAndroidPackageName(
                    "com.example.bitcointickerapp",
                    false,
                    null
                )
            }

            auth.sendSignInLinkToEmail(email, actionCodeSettings)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Email sent!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "An Error Occurred!", Toast.LENGTH_LONG).show()
                    }
                }


        } else {
            //var emailLink1="https://bitcointickerapp.firebaseapp.com/__/auth/action?apiKey%3DAIzaSyCyAjHgQigjcXM9cH5xslpFA6VNfExhuZs%26mode%3DsignIn%26oobCode%3D6LPOYrgK_by_E-AqfVNEejHpCEhEON9oxgiHzkkK8cEAAAF6T2W3YA%26continueUrl%3Dhttps://bitcointickerapp.page.link%26lang%3Den"
            if (!auth.isSignInWithEmailLink(emailLink)) {

                auth.signInWithEmailLink("mehmethasanakcay@gmail.com", emailLink)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Login Successful!", Toast.LENGTH_LONG).show()
                            val action = Intent(context, HomeFragment::class.java)
                            context.startActivity(action)
                        } else {
                            Toast.makeText(
                                context,
                                "Error signing in with email link!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }

        }

    }

    fun registerWithEmailAndPassword(context: Context, email: String, password : String){
        auth = FirebaseAuth.getInstance()
        if (email != "" && password != ""){
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(context, "Register successful!", Toast.LENGTH_LONG).show()
                    val action = Intent(context, MainActivity::class.java)
                    context.startActivity(action)
                    val activity = context as Activity
                    activity.finish()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(context, "Please fill in the blanks!", Toast.LENGTH_LONG).show()
        }

    }

    fun signInWithEmailAndPassword(context: Context, email: String, password : String){
        auth = FirebaseAuth.getInstance()
        if (email != "" && password != ""){
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(context, "Login successful!", Toast.LENGTH_LONG).show()
                    val action = Intent(context, MainActivity::class.java)
                    context.startActivity(action)
                    val activity = context as Activity
                    activity.finish()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(context, "Please fill in the blanks!", Toast.LENGTH_LONG).show()
        }

    }

}