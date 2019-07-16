package com.example.mybookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private var mAuth: FirebaseAuth? = null

    private val TAG = "RegisterEmailPassword"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setSupportActionBar(findViewById(R.id.reg_toolbar))

        val actionBar = supportActionBar

        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        register_btn.setOnClickListener(this)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onClick(view: View?) {
        val i = view!!.id

        if (i == R.id.register_btn){
            createAccount(reg_email_input.text.toString(), reg_password_input.text.toString().trim())
        }
    }

    private fun createAccount(email: String, password: String){
        Log.e(TAG, "createAccount:" + email)
        if (!validateForm(email,password)){
            return
        }

        mAuth!!.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if (it.isSuccessful){
                Log.e(TAG, "createAccount: Success!")
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Log.e(TAG, "createAccount: Fail!", it.exception)
                Toast.makeText(applicationContext, "Authentication failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            Toast.makeText(applicationContext, "Please enter your email and password!", Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(email)){
            Toast.makeText(applicationContext, "Please enter your email!", Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(password)){
            Toast.makeText(applicationContext, "Please enter your password!", Toast.LENGTH_SHORT).show()
            return false
        } else if (password.length < 6){
            Toast.makeText(applicationContext, "Your password is too short! Please enter minimum 6 characters!",
                Toast.LENGTH_SHORT).show()
            return false
        }

        return true
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
