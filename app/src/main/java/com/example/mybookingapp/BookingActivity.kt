package com.example.mybookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mybookingapp.model.Bookings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class BookingActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    lateinit var bookingName: EditText
    lateinit var bookingCinema: EditText
    lateinit var bookingMovie: EditText
    lateinit var bookingbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        setSupportActionBar(findViewById(R.id.booking_toolbar))

        val actionBar = supportActionBar

        actionBar!!.title = "Booking"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        bookingName = findViewById(R.id.booking_name)
        bookingCinema = findViewById(R.id.booking_cinema)
        bookingMovie = findViewById(R.id.booking_movie)
        bookingbtn = findViewById(R.id.submit)

        bookingbtn.setOnClickListener {
            addBooking()
        }

        mAuth = FirebaseAuth.getInstance()
    }

    private fun addBooking() {
        val name = bookingName.text.toString().trim()
        val cinema = bookingCinema.text.toString().trim()
        val movie = bookingMovie.text.toString().trim()

        if (name.isEmpty()) {
            bookingName.error = "Please Enter Your Name And Surname!"
            return
        } else if (cinema.isEmpty()) {
            bookingCinema.error = "Please Enter The Cinema You Want to Book a Ticket For!"
            return
        } else if (movie.isEmpty()) {
            bookingMovie.error = "Please Enter The Movie You Want to See!"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("Bookings")
        val bookId = ref.push().key

        val book = Bookings(bookId, name, cinema ,movie)

        ref.child(bookId!!).setValue(book).addOnCompleteListener {
            Toast.makeText(applicationContext, "Your Booking Was Saved Successfully", Toast.LENGTH_LONG).show()
            bookingName.setText("")
            bookingCinema.setText("")
            bookingMovie.setText("")
        }
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
