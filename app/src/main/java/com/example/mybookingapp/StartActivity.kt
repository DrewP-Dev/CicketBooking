package com.example.mybookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth


class StartActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

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

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth!!.currentUser

        if (currentUser != null){
            sendToStart()

        }
    }

    private fun sendToStart(){
        val startIntent = Intent(applicationContext, MainActivity::class.java)
        startActivity(startIntent)
        finish()
    }
}
