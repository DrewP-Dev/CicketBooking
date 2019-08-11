package com.example.mybookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class AboutActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setSupportActionBar(findViewById(R.id.about_toolbar))

        val actionBar = supportActionBar

        actionBar!!.title = "About"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.log_out_btn ->{
                mAuth!!.signOut()
                sendToStart()
            }
            else ->{
                onBackPressed()
            }
        }
        return true
    }

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth!!.currentUser

        if (currentUser == null){
            sendToStart()

        }
    }

    private fun sendToStart(){
        val startIntent = Intent(applicationContext, StartActivity::class.java)
        startActivity(startIntent)
        finish()
    }
}
