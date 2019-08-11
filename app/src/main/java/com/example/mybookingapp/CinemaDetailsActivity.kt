package com.example.mybookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cinema_details.*

class CinemaDetailsActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cinema_details)

        val bundle: Bundle? = intent.extras
        val cName = bundle!!.getString("Cinema_Name")
        val cPhoto = bundle.getString("Cinema_Photo")
        val cLocation = bundle.getString("Cinema_Location")
        val cAddress = bundle.getString("Cinema_Address")
        val cContact = bundle.getString("Cinema_Contact")

        setSupportActionBar(findViewById(R.id.cinema_details_toolbar))

        val actionBar = supportActionBar

        actionBar!!.title = cName

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        cinema_details_name.setText(cName)
        Picasso.get().load(cPhoto).into(cinema_details_img)
        cinema_details_location.setText(cLocation)
        cinema_details_address.setText(cAddress)
        cinema_details_contact.setText(cContact)

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

    private fun sendToStart() {
        val startIntent = Intent(applicationContext, StartActivity::class.java)
        startActivity(startIntent)
        finish()
    }
}
