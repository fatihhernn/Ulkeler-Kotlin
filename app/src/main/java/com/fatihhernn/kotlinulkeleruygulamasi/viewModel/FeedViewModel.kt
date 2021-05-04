package com.fatihhernn.kotlinulkeleruygulamasi.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fatihhernn.kotlinulkeleruygulamasi.model.Country
import com.fatihhernn.kotlinulkeleruygulamasi.service.CountryAPI
import com.fatihhernn.kotlinulkeleruygulamasi.service.CountryAPIService
import com.fatihhernn.kotlinulkeleruygulamasi.service.CountryDatabase
import com.fatihhernn.kotlinulkeleruygulamasi.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FeedViewModel(application: Application): BaseViewModel(application){

    private val countryAPIService=CountryAPIService()
    //çağırılan objeler bunu içine atılır. işi bitince siler
    private val disposable=CompositeDisposable()
    private var customPreferences=CustomSharedPreferences(getApplication())
    private var refreshTime=10*60*1000*1000*1000L //10 DK


    val countries= MutableLiveData<List<Country>>()
    val countryError= MutableLiveData<Boolean>()
    val countryLoading= MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime=customPreferences.getTime();
        if (updateTime!=null&&updateTime!=0L&&System.nanoTime()-updateTime<refreshTime){
            getDataFromSQLite()
        }
        else{
            getDataFromAPI()
        }

    }

    fun refreshFromAPI() {
        getDataFromAPI()
    }

    private fun getDataFromSQLite() {
        launch {
            val countries=CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries From SQLite",Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI(){
        countryLoading.value=true

        disposable.add(
                countryAPIService.getData()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                            //apiden almak başarılı ise bunları yapıyoruz
                            override fun onSuccess(t: List<Country>) {
                                storeInSQLite(t)
                                Toast.makeText(getApplication(),"Countries From API",Toast.LENGTH_LONG).show()
                            }

                            override fun onError(e: Throwable) {
                                countryLoading.value=false
                                countryError.value=true
                                e.printStackTrace()
                            }

                        })
        )
    }

    private fun showCountries(countryList:List<Country>){
        countries.value=countryList
        countryError.value=false
        countryLoading.value=false
    }

    private fun storeInSQLite(list:List<Country>){
        launch {
            val dao=CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong=dao.insertAll(*list.toTypedArray())

            var i=0
            while (i<list.size){
                list[i].uuid=listLong[i].toInt()
                i=i+1
            }
            showCountries(list)
        }
        customPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}