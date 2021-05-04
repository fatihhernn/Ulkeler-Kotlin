package com.fatihhernn.kotlinulkeleruygulamasi.service

import com.fatihhernn.kotlinulkeleruygulamasi.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {

    //GET, POST => işlemleri yapabiliriz

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<Country>>

}