package com.example.practicalapp



import android.content.ClipData.newIntent
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practicalapp.activity.MainActivity
import com.example.practicalapp.base.BaseActivity
import com.example.practicalapp.databinding.ActivitySplashBinding
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    lateinit var destIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading()

        initView()

    }

    private fun initView() {
         destIntent = Intent(this,MainActivity::class.java)
        Timer("afterSplash", false)
            .schedule(TimeUnit.SECONDS.toMillis(2)) {
                startActivity(destIntent)
                finish()
                hideLoading()
            }


    }


}