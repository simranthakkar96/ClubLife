package com.example.Clublife

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.Clublife.Common.Common
import com.example.Clublife.Model.APIResponse
import com.example.Clublife.Remote.IMyAPI
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    internal lateinit var mService:IMyAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val username = findViewById (R.id.et_username) as EditText
        val password = findViewById (R.id.et_password) as EditText

        val sharedpreferences = getSharedPreferences("UID", Context.MODE_PRIVATE)

        if(sharedpreferences.getBoolean("logged",false)){
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {

            authenticateUser(username.text.toString(),password.text.toString())


        }

        btn_register.setOnClickListener {

            val intent = Intent(this, RegisterPage::class.java)
            // start your next activity
            startActivity(intent)


        }
        //Initialize service
        mService = Common.api
        txt_forgot.underline()
    }




private fun authenticateUser(username:String,password:String){
        mService.loginUser(username, password).enqueue(object : Callback<APIResponse>{

            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity,t.message,Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                if(response.body()!!.error)
                {
                    Toast.makeText(this@MainActivity,response.body()!!.error_msg,Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@MainActivity,"Login Succesfull",Toast.LENGTH_SHORT).show()


                    val sharedpreferences = getSharedPreferences("UID", Context.MODE_PRIVATE)


                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(intent)

                    sharedpreferences.edit().putString("ID",response.body()!!.uid.toString()).apply()
                    sharedpreferences.edit().putString("USERNAME",response.body()!!.user!!.username).apply()
                    sharedpreferences.edit().putString("NAME",response.body()!!.user!!.name).apply()
                    sharedpreferences.edit().putString("EMAIL",response.body()!!.user!!.email).apply()
                    sharedpreferences.edit().putString("AGE",response.body()!!.user!!.age).apply()



                }
            }

        })


    }


    fun TextView.underline() {
        paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
}
