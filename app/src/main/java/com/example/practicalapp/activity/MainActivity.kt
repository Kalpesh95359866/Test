package com.example.practicalapp.activity

import android.os.Bundle
import android.widget.Toast
import com.example.practicalapp.R
import com.example.practicalapp.base.BaseActivity
import com.example.practicalapp.databinding.ActivityMainBinding
import com.example.practicalapp.fragment.home.HomeFragment
import com.example.practicalapp.fragment.practices.PracticesFragment
import com.example.practicalapp.fragment.profile.ProfileFragment

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // setContentView(R.layout.activity_main)

        replaceFragment(HomeFragment(), true)

        setNavigationView(binding)

    }

    private fun setNavigationView(binding: ActivityMainBinding) {
        binding.navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_Home -> {
                    clearAllFragment()
                    replaceFragment(HomeFragment(), true)
                }
                R.id.menu_Lessons -> {
                    replaceFragment(PracticesFragment(), true)
                }
                R.id.menu_mediation ->
                    Toast.makeText(this, "Mediation", Toast.LENGTH_LONG).show()
                R.id.menu_Profile -> {
                    replaceFragment(ProfileFragment(),true)

                }
            }
            true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }


}