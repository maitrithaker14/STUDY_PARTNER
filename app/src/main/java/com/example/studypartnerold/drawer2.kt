package com.example.studypartnerold

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studypartnerold.databinding.ActivityAddTaskBinding.inflate
import com.example.studypartnerold.databinding.ActivityDrawer2Binding
import com.example.studypartnerold.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase



class drawer2 : AppCompatActivity() {
    lateinit var toggle : ActionBarDrawerToggle
    private var navView:NavigationView?=null
    private var pencil: ImageView?=null

    lateinit var auth: FirebaseAuth

    //variable
    private lateinit var binding: ActivityDrawer2Binding
    private lateinit var ref: DatabaseReference
    private lateinit var adapter: TodoAdapter
    private lateinit var todolist: MutableList<Todo>
    private lateinit var drawerlayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_drawer2)
        binding = ActivityDrawer2Binding.inflate(layoutInflater)
        //setContentView(binding.root)

        drawerlayout = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawerlayout, R.string.open, R.string.close)

        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setTitle("")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        pencil = findViewById(R.id.pencil)

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser.toString()

        todolist = mutableListOf()

        recyclerView()




        pencil!!.setOnClickListener {
            startActivity(Intent(this, addTask::class.java))
        }

        navView = findViewById<NavigationView>(R.id.nav_view)
        navView!!.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profile_drawer -> {
                    val intent = Intent(this, profilesetup::class.java)
                    startActivity(intent)
                }
                R.id.quiz_drawer -> {
                    val intent = Intent(this, Quizes::class.java)
                    startActivity(intent)
                }

                R.id.ask_me_drawer -> {
                    val intent = Intent(this, Askme::class.java)
                    startActivity(intent)
                }
                R.id.logout_drawer -> {
                    Firebase.auth.signOut()
                    val intent = Intent(this, login::class.java)
                    startActivity(intent)
                }

            }
            true
        }


    }

    override fun onStart() {
        super.onStart()
        ref = FirebaseDatabase.getInstance().getReference("TaskNote")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    todolist.clear()
                    for (ds in snapshot.children) {
                        val todo = ds.getValue<Todo>()
                        if (todo != null) {
                            todolist.add(todo)
                            adapter.setData(todolist)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "" + error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun recyclerView() {
        adapter = TodoAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_id)
            recyclerView.layoutManager = LinearLayoutManager(this@drawer2)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }
    }


}




