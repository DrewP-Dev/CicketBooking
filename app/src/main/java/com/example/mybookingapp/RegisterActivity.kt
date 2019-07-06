package com.example.mybookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegisterActivity : AppCompatActivity() {

    lateinit var mRegisterBtn : Button
    lateinit var mHaveAccountBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mRegisterBtn = findViewById(R.id.register_btn)
        mHaveAccountBtn = findViewById(R.id.have_account_btn)

        mRegisterBtn.setOnClickListener {
            val intent = Intent(applicationContext , MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        mHaveAccountBtn.setOnClickListener {
            val intent = Intent(applicationContext , LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
