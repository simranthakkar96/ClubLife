package com.example.Clublife.Remote

import com.example.Clublife.Model.APIResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface IMyAPI {
    @FormUrlEncoded
    @POST("register.php")
    fun registerUser(@Field("username")username:String,@Field("email")email:String,@Field("name")name:String,@Field("age")age:String,@Field("gender")gender:String,@Field("preferred_gender")preferred_gender:String,@Field("password")password:String): Call<APIResponse>


    @FormUrlEncoded
    @POST("login.php")
    fun loginUser(@Field("username") username:String,@Field("password") password:String):Call<APIResponse>

    @FormUrlEncoded
    @POST("update.php")
    fun updateUser(@Field("unique_id")unique_id:String,@Field("username") username:String,@Field("email") email:String,@Field("name") name:String,@Field("age") age:String):Call<APIResponse>

}