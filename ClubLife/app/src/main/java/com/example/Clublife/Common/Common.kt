package com.example.Clublife.Common

import com.example.Clublife.Remote.IMyAPI
import com.example.Clublife.Remote.RetrofitClient

object Common {
    val BASE_URL = "http://192.168.0.18/clubapi/"

    val api: IMyAPI
    get() = RetrofitClient.getClient(BASE_URL).create(IMyAPI::class.java)
}