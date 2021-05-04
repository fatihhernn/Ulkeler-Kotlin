package com.fatihhernn.kotlinulkeleruygulamasi.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fatihhernn.kotlinulkeleruygulamasi.model.Country
import com.fatihhernn.kotlinulkeleruygulamasi.service.CountryDatabase
import kotlinx.coroutines.launch
import java.util.*

class CountryViewModel(application: Application): BaseViewModel(application) {
    val countryLiveData=MutableLiveData<Country>();

    fun getDataFromRoom(uuid: Int){
        launch {
            val dao=CountryDatabase(getApplication()).countryDao()
            val country= dao.getCountry(uuid)
            countryLiveData.value=country
        }
    }
}