package com.example.mybookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var mAuth: FirebaseAuth? = null

    private val TAG = "LoginEmailPassword"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(findViewById(R.id.log_toolbar))

        val actionBar = supportActionBar

        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        login_btn.setOnClickListener(this)
        forgot_password.setOnClickListener(this)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onClick(view: View?) {
        val i = view!!.id

        if (i == R.id.login_btn){
            signIn(log_email_input.text.toString(), log_password_input.text.toString().trim())
        }
        if (i == R.id.forgot_password){
            val intent = Intent(applicationContext , ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(email: String, password: String){
        Log.e(TAG, "loginAccount:" + email)
        if (!validateForm(email,password)){
            return
        }
        updateUI(true)
        mAuth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if (it.isSuccessful){
                Log.e(TAG, "signIn: Success!")
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Log.e(TAG, "signIn: Fail!", it.exception)
                Toast.makeText(applicationContext, "Authentication failed!", Toast.LENGTH_SHORT).show()
                updateUI(false)
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
        } else if (password.length < 8){
            Toast.makeText(applicationContext, "Your password is too short! Please enter minimum 6 characters!",
                Toast.LENGTH_SHORT).show()
            return false
        } else if (password.length > 128){
            Toast.makeText(applicationContext, "Your password is too long! Please enter maximum 128 characters!",
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
        } else {
            updateUI(false)
        }
    }

    private fun updateUI(boolean: Boolean){
        if (boolean) {
            log_email_input.visibility = View.GONE
            log_password_input.visibility = View.GONE
            login_btn.visibility = View.GONE
            forgot_password.visibility = View.GONE
            log_loading.visibility = View.VISIBLE
        } else {
            log_email_input.visibility = View.VISIBLE
            log_password_input.visibility = View.VISIBLE
            login_btn.visibility = View.VISIBLE
            forgot_password.visibility = View.VISIBLE
            log_loading.visibility = View.GONE
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
