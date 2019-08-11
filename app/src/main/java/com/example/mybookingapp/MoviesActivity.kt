package com.example.mybookingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybookingapp.model.Movie
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class MoviesActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    lateinit var mrecylerview : RecyclerView
    lateinit var ref: DatabaseReference
    lateinit var show_progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        setSupportActionBar(findViewById(R.id.movies_toolbar))

        val actionBar = supportActionBar

        actionBar!!.title = "Movies"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        ref = FirebaseDatabase.getInstance().getReference().child("Movies")
        mrecylerview = findViewById(R.id.m_recyclerview)
        mrecylerview.layoutManager = GridLayoutManager(this, 2)

        show_progress = findViewById(R.id.m_progress_bar)

        firebaseData()

        mAuth = FirebaseAuth.getInstance()
    }

    fun firebaseData() {
        val option = FirebaseRecyclerOptions.Builder<Movie>()
            .setQuery(ref, Movie::class.java)
            .setLifecycleOwner(this)
            .build()

        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Movie, MyViewHolder>(option){

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@MoviesActivity).inflate(R.layout.movies_row,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, potition: Int, model: Movie) {
                val placeid = getRef(potition).key.toString()

                ref.child(placeid).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(this@MoviesActivity, "Error Occurred " + p0.toException(), Toast.LENGTH_SHORT)
                            .show()
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        show_progress.visibility = if(itemCount == 0) View.VISIBLE else View.GONE

                        holder.txt_name.setText(model.mtitle)
                        Picasso.get().load(model.mphotoUrl).into(holder.img_vet)

                        holder.itemView.setOnClickListener {
                            val intent = Intent(this@MoviesActivity, MovieDetailsActivity::class.java)
                            intent.putExtra("Movie_Title", model.mtitle)
                            intent.putExtra("Movie_Photo", model.mphotoUrl)
                            intent.putExtra("Movie_Tag", model.mtag)
                            intent.putExtra("Movie_Director", model.mdirector)
                            intent.putExtra("Movie_Cast", model.mcast)
                            intent.putExtra("Movie_Description", model.mdescription)
                            startActivity(intent)
                        }

                    }
                })
            }
        }
        mrecylerview.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        internal var txt_name: TextView = itemView!!.findViewById(R.id.movie_title)
        internal var img_vet: ImageView = itemView!!.findViewById(R.id.movie_image)

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
