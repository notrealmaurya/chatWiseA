package com.maurya.chatwiseassignment

import android.app.Application
import com.maurya.chatwiseassignment.repository.Product
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ApplicationClass : Application() {

    var productList: ArrayList<Product> = arrayListOf()

    override fun onCreate() {
        super.onCreate()
    }
}