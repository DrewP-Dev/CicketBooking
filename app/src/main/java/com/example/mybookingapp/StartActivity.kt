package com.example.mybookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_start.*



class StartActivity : AppCompatActivity(), View.OnClickListener {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        create_account_btn.setOnClickListener(this)
        sign_in_btn.setOnClickListener(this)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onClick(view: View?) {
        val i = view!!.id

        if (i == R.id.create_account_btn){
            val intent = Intent(applicationContext , RegisterActivity::class.java)
            startActivity(intent)
        }
        if (i == R.id.sign_in_btn){
            val intent = Intent(applicationContext , LoginActivity::class.java)
            startActivity(intent)
        }
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
