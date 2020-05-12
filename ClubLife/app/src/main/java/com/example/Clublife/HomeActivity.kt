package com.example.Clublife

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.custom_action_bar.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val actionBar: ActionBar? = this.supportActionBar
        actionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        actionBar?.setDisplayShowCustomEnabled(true)
        actionBar?.setCustomView(R.layout.custom_action_bar)

        logout.setOnClickListener {

            val sharedpreferences = getSharedPreferences("UID", Context.MODE_PRIVATE)
            sharedpreferences.edit().remove("ID").apply()

            val intent = Intent(this@HomeActivity, MainActivity::class.java)
            startActivity(intent)
        }

        if (savedInstanceState == null) {
            val fragment = FragmentMap()
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, fragment.javaClass.getSimpleName())
                .commit()
        }

        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

        private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_nearby -> {
                val fragment = FragmentMap()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                val fragment = ProfileFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


}
