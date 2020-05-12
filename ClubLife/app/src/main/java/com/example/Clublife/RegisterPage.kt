package com.example.Clublife

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Paint
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.Clublife.Common.Common
import com.example.Clublife.Model.APIResponse
import com.example.Clublife.Remote.IMyAPI
import kotlinx.android.synthetic.main.activity_register_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterPage : AppCompatActivity() {

    internal lateinit var mService: IMyAPI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialize service
        mService = Common.api

        var gender = arrayOf("Male", "Female", "Other")
        var preferred_gender = arrayOf("Male","Female","Other")

        // Initializing an ArrayAdapter
        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this, R.layout.spinner_item, gender
        )
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item)
        spinner_gender.setAdapter(spinnerArrayAdapter)


        //Intializiong second arrayadapter
        val spinnerArrayAdapter2: ArrayAdapter<String> = ArrayAdapter<String>(
            this, R.layout.spinner_item, preferred_gender
        )
        spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_item)
        spinner_preferred.setAdapter(spinnerArrayAdapter2)


        txt_login.setOnClickListener {
            finish()
        }

        btn_register.setOnClickListener {
            createNewUser(
                et_uname.text.toString(),
                et_email.text.toString(),
                et_name.text.toString(),
                et_age.text.toString(),
                spinner_gender.selectedItem.toString(),
                spinner_preferred.selectedItem.toString(),
                et_pass.text.toString()
            )
        }

        txt_login.underline()

        profile.setOnClickListener(View.OnClickListener {
            val options =
                arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
            val builder =
                AlertDialog.Builder(this@RegisterPage)
            builder.setTitle("Choose your profile picture")
            builder.setItems(
                options
            ) { dialog, item ->
                if (options[item] == "Take Photo") {
                    val takePicture =
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePicture, 0)
                } else if (options[item] == "Choose from Gallery") {
                    val pickPhoto = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(pickPhoto, 1)
                } else if (options[item] == "Cancel") {
                    dialog.dismiss()
                }
            }
            builder.show()
        })
    }
    fun TextView.underline() {
        paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_CANCELED) {
            when (requestCode) {
                0 -> if (resultCode == Activity.RESULT_OK && data != null) {
                    val selectedImage = data.extras!!["data"] as Bitmap?
                    profile.setImageBitmap(selectedImage)
                }
                1 -> if (resultCode == Activity.RESULT_OK) {
                    val selectedImage = data!!.data
                    profile.setImageURI(selectedImage)
                }
            }
        }
    }
    private fun createNewUser(username:String,email:String,name:String,age:String,gender:String,preferred_gender:String,password:String)
    {
        mService.registerUser(username,email,name,age,gender,preferred_gender,password).enqueue(object : Callback<APIResponse>
        {
            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Toast.makeText(this@RegisterPage,t.message,Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                if(response.body()!!.error)
                {
                    Toast.makeText(this@RegisterPage,response.body()!!.error_msg,Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@RegisterPage,"Registered Succesfull"+response.body()!!.uid,Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

        })
    }
}
