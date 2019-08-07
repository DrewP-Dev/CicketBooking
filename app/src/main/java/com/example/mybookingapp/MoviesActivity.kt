package com.example.mybookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

class MoviesActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        setSupportActionBar(findViewById(R.id.movies_toolbar))

        val actionBar = supportActionBar

        actionBar!!.title = "Movies"

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
            R.id.about_btn ->{
                val intent = Intent(applicationContext , AboutActivity::class.java)
                startActivity(intent)
            }
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
