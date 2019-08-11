package com.example.mybookingapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.mybookingapp.R.id.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_drawer.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        init()

        val actionBar = supportActionBar

        actionBar!!.title = "Cicket Booking"

        mAuth = FirebaseAuth.getInstance()
    }

    private fun init() {
        val toggle = ActionBarDrawerToggle(Activity(), drawer_layout, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navigation_view.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            booking_btn->{
                val intent = Intent(applicationContext , BookingActivity::class.java)
                startActivity(intent)
            }
            movies_btn->{
                val intent = Intent(applicationContext , MoviesActivity::class.java)
                startActivity(intent)
            }
            cinemas_btn->{
                val intent = Intent(applicationContext , CinemasActivity::class.java)
                startActivity(intent)
            }
            about_btn ->{
                val intent = Intent(applicationContext , AboutActivity::class.java)
                startActivity(intent)
            }
            else ->{
                Toast.makeText(applicationContext, "ERROR!", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            log_out_btn ->{
                mAuth!!.signOut()
                sendToStart()
            }
            else ->{
                Toast.makeText(applicationContext, "ERROR!", Toast.LENGTH_SHORT).show()
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
