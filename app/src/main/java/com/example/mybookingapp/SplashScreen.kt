package com.example.mybookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Timer().schedule( object : TimerTask(){
            override fun run() {
                val intent = Intent(applicationContext , StartActivity::class.java)
                startActivity(intent)
                finish()
            }
        } , 2000L)
    }
}
