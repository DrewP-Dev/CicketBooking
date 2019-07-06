package com.example.mybookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {

    lateinit var mLoginBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mLoginBtn = findViewById(R.id.login_btn)

        mLoginBtn.setOnClickListener {
            val intent = Intent(applicationContext , MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
