package com.example.mybookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartActivity : AppCompatActivity() {

    lateinit var mCreateAccountBtn : Button
    lateinit var mSignInBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        mCreateAccountBtn = findViewById(R.id.create_account_btn)
        mSignInBtn = findViewById(R.id.sign_in_btn)

        mCreateAccountBtn.setOnClickListener {
            val intent = Intent(applicationContext , RegisterActivity::class.java)
            startActivity(intent)
        }

        mSignInBtn.setOnClickListener {
            val intent = Intent(applicationContext , LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
