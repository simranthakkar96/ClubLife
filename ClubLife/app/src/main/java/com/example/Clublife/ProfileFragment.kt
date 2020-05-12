package com.example.Clublife

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.Clublife.Common.Common
import com.example.Clublife.Model.APIResponse
import com.example.Clublife.Remote.IMyAPI
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    internal lateinit var mService: IMyAPI


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //val sharedpreferences = getSharedPreferences("UID", Context.MODE_PRIVATE)
        mService = Common.api



        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onResume() {
        super.onResume()

        val sharedprefrences = activity!!.getSharedPreferences("UID",Context.MODE_PRIVATE)
        val id = sharedprefrences.getString("ID","")
        val username = sharedprefrences.getString("USERNAME","")
        val name = sharedprefrences.getString("NAME","")
        val email = sharedprefrences.getString("EMAIL","")
        val age = sharedprefrences.getString("AGE","")

        et_funame.setText(username)
        et_name.setText(name)
        et_email.setText(email)
        et_age.setText(age)

        btn_update.setOnClickListener {
            updateUser(
                id.toString(),
                et_funame.text.toString(),
                et_email.text.toString(),
                et_name.text.toString(),
                et_age.text.toString())
        }
    }





    private fun updateUser(unique_id:String,username:String,email:String,name:String,age:String)
    {
        mService.updateUser(unique_id,username,email,name,age).enqueue(object :
            Callback<APIResponse>
        {
            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Toast.makeText(context,t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                if(response.body()!!.error)
                {
                    Toast.makeText(context,response.body()!!.error_msg, Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context,"Updated Succesfull"+response.body()!!.uid, Toast.LENGTH_SHORT).show()

                }
            }

        })
    }
}

