package com.fatihhernn.kotlinulkeleruygulamasi.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fatihhernn.kotlinulkeleruygulamasi.model.Country

@Dao
interface CountryDao {

    @Insert
    suspend fun insertAll(vararg countries:Country):List<Long>

    //insert => INSERT INTO ..
    //suspend => coroutines içerisinde çağrılıyor, durdurulup devam ettirilebilin
    //vararg => istediğimiz zaman istediğimiz kadar parametre geçmeyi sağlayan keyword => multiple country objects döndürür

    @Query("SELECT * FROM country")
    suspend fun getAllCountries():List<Country>

    @Query("SELECT * FROM country WHERE uuid=:countryId")
    suspend fun getCountry(countryId:Int):Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()

}