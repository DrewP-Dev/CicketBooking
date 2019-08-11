package com.example.mybookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val bundle: Bundle? = intent.extras
        val mTitle = bundle!!.getString("Movie_Title")
        val mPhoto = bundle.getString("Movie_Photo")
        val mTag = bundle.getString("Movie_Tag")
        val mDirector = bundle.getString("Movie_Director")
        val mCast = bundle.getString("Movie_Cast")
        val mDescription = bundle.getString("Movie_Description")

        setSupportActionBar(findViewById(R.id.movie_details_toolbar))

        val actionBar = supportActionBar

        actionBar!!.title = mTitle

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        movie_details_title.setText(mTitle)
        Picasso.get().load(mPhoto).into(movie_details_img)
        movie_details_tag.setText("Tags: " + mTag)
        movie_details_director.setText("Director: " + mDirector)
        movie_details_cast.setText("Cast: " + mCast)
        movie_details_description.setText(mDescription)

        show_desc_btn.setOnClickListener(this)
        hide_desc_btn.setOnClickListener(this)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onClick(view: View?) {
        val i = view!!.id

        if (i == R.id.show_desc_btn){
            updateUI(true)
        }
        if (i == R.id.hide_desc_btn){
            updateUI(false)
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
        } else {
            updateUI(false)
        }
    }

    private fun updateUI(boolean: Boolean){
        if (boolean) {
            show_desc_btn.visibility = View.GONE
            hide_desc_btn.visibility = View.VISIBLE
            movie_details_description.visibility = View.VISIBLE
        } else {
            show_desc_btn.visibility = View.VISIBLE
            hide_desc_btn.visibility = View.GONE
            movie_details_description.visibility = View.GONE
        }
    }

    private fun sendToStart() {
        val startIntent = Intent(applicationContext, StartActivity::class.java)
        startActivity(startIntent)
        finish()
    }
}
