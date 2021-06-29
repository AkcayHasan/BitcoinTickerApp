package com.example.bitcointickerapp.viewModel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.*
import com.example.bitcointickerapp.data.CryptoDatabase
import com.example.bitcointickerapp.model.CryptoModel
import com.example.bitcointickerapp.network.RetrofitInstance
import com.example.bitcointickerapp.view.LoginActivity
import com.example.bitcointickerapp.view.MainActivity
import com.example.bitcointickerapp.view.fragments.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val retrofitInstance = RetrofitInstance()
    private val disposable : CompositeDisposable = CompositeDisposable()
    private lateinit var auth: FirebaseAuth

    val cryptoCoins = MutableLiveData<List<CryptoModel>>()
    val cryptoProgressBar = MutableLiveData<Boolean>()

    companion object{
        const val CURRENCY_TYPE = "usd"
    }

    fun getAllDataFromApi(){
        cryptoProgressBar.value = true
        disposable.add(
            retrofitInstance.getAllDataFromApi(CURRENCY_TYPE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CryptoModel>>(){
                    override fun onSuccess(t: List<CryptoModel>) {
                        saveToSqlite(t)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
        )
    }

    fun refreshData(){
        getAllDataFromApi()
    }

    private fun showCoins(coinList : List<CryptoModel>){
        cryptoCoins.value = coinList
    }

    private fun saveToSqlite(coinList: List<CryptoModel>){
        val thread = Thread{
            val dataAccessObject = CryptoDatabase(getApplication()).cryptoDao()
            dataAccessObject.deleteAllCoins()
            val idList = dataAccessObject.insertAllCryptoCoins(*coinList.toTypedArray())
            var i = 0

            while (i < coinList.size){
                coinList[i].specialKey = idList[i].toInt()
                i++
            }
        }
        thread.start()
        showCoins(coinList)
    }

    fun searchDatabase(searchQuery : String) : LiveData<List<CryptoModel>> {
        val dataAccessObject = CryptoDatabase(getApplication()).cryptoDao()
        return dataAccessObject.searchDatabase(searchQuery).asLiveData()
    }

    fun signOutFirebase(context: Context){
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        val action = Intent(context, LoginActivity::class.java)
        context.startActivity(action)
    }



}