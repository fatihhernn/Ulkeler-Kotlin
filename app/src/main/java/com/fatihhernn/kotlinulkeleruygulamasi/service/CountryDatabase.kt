package com.fatihhernn.kotlinulkeleruygulamasi.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fatihhernn.kotlinulkeleruygulamasi.model.Country

@Database(entities = [Country::class],version = 1)
abstract class CountryDatabase :RoomDatabase() {
    abstract fun countryDao():CountryDao

    //singleton : içerisinde 1 tane obje oluşturulabilir ve app'in heryerinde oluşturulabilir

    //companion = statiklik kazandırır, her yerde oluşturturulabilir
    companion object{

        //@Volatile : farklı threadlerde çağrılabilir, görünür hale getirebilyor. sınıfı singletan yapmanın amacı threadlarda çağırmak
        @Volatile private var instance:CountryDatabase?=null

        private val lock=Any()
        //instance var mı yok mu baktırıp tetikleyelim
        //synchronized : aynı anda iki thread bu instance ulaşamıyor. o işlem bittikten sonra diğer thrad ler çalışacak
        operator fun invoke(context:Context)= instance?: synchronized(lock){
            instance?: makeDatabase(context).also {
                instance=it
            }
        }
        private fun makeDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,CountryDatabase::class.java,"countrydatabase").build()


    }
}