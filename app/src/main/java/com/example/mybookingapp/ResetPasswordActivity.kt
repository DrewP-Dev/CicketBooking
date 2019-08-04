package com.example.mybookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity(), View.OnClickListener {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        setSupportActionBar(findViewById(R.id.reset_toolbar))

        val actionBar = supportActionBar

        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        reset_btn.setOnClickListener(this)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onClick(view: View?) {
        val i = view!!.id

        if (i == R.id.reset_btn){
            resetPassword(reset_email_input.text.toString())
        }
    }

    private fun resetPassword(email: String){
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Enter your email!", Toast.LENGTH_SHORT).show()
        } else {
            mAuth!!.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Check your email to reset your password!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Fail to send reset password email!", Toast.LENGTH_SHORT).show()
                }
            }
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

