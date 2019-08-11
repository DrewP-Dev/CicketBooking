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
import com.example.mybookingapp.model.Cinema
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class CinemasActivity : AppCompatActivity() {

    lateinit var crecylerview : RecyclerView
    lateinit var ref: DatabaseReference
    lateinit var show_progress: ProgressBar

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cinemas)

        setSupportActionBar(findViewById(R.id.cinemas_toolbar))

        val actionBar = supportActionBar

        actionBar!!.title = "Cinemas"

        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        ref = FirebaseDatabase.getInstance().getReference().child("Cinemas")
        crecylerview = findViewById(R.id.c_recyclerview)
        crecylerview.layoutManager = GridLayoutManager(this, 2)

        show_progress = findViewById(R.id.c_progress_bar)

        firebaseData()

        mAuth = FirebaseAuth.getInstance()
    }

    fun firebaseData() {
        val option = FirebaseRecyclerOptions.Builder<Cinema>()
            .setQuery(ref, Cinema::class.java)
            .setLifecycleOwner(this)
            .build()

        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Cinema, MyViewHolder>(option){

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@CinemasActivity).inflate(R.layout.cinemas_row,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, potition: Int, model: Cinema) {
                val placeid = getRef(potition).key.toString()

                ref.child(placeid).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(this@CinemasActivity, "Error Occurred " + p0.toException(), Toast.LENGTH_SHORT)
                            .show()
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        show_progress.visibility = if(itemCount == 0) View.VISIBLE else View.GONE

                        holder.txt_name.setText(model.cname)
                        Picasso.get().load(model.cphoto).into(holder.img_vet)

                        holder.itemView.setOnClickListener {
                            val intent = Intent(this@CinemasActivity, CinemaDetailsActivity::class.java)
                            intent.putExtra("Cinema_Name", model.cname)
                            intent.putExtra("Cinema_Photo", model.cphoto)
                            intent.putExtra("Cinema_Location", model.clocation)
                            intent.putExtra("Cinema_Address", model.caddress)
                            intent.putExtra("Cinema_Contact", model.ccontact)
                            startActivity(intent)
                        }

                    }
                })
            }
        }
        crecylerview.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        internal var txt_name: TextView = itemView!!.findViewById(R.id.cinema_name)
        internal var img_vet: ImageView = itemView!!.findViewById(R.id.cinema_image)

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
