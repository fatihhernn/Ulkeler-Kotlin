package com.fatihhernn.kotlinulkeleruygulamasi.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fatihhernn.kotlinulkeleruygulamasi.R

//extensiton oluşturulacaksa
/*
fun String.myExtension(myParameter:String){
    println(myParameter)
}

 */

fun ImageView.downloadFromUrl(url:String?,progressDrawable: CircularProgressDrawable) {

    //hata olursa => placeholder
    val options=RequestOptions()
            .placeholder(progressDrawable)
            .error(R.mipmap.ic_launcher)

    Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(url)
            .into(this)
}

//placeholder yerine gelecek dönen daire getirme
fun placeHolderProgressBar(context:Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth=8f
        centerRadius=40f
        start()
    }
}
//xml'de çalıştırmak
@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView,url: String?){
    view.downloadFromUrl(url, placeHolderProgressBar(view.context))
}