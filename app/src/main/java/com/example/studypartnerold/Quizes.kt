package com.example.studypartnerold

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Quizes : AppCompatActivity() {
    lateinit var toggle : ActionBarDrawerToggle
    var drawerlayout:DrawerLayout?=null
    var navView:NavigationView?=null
    var grid: GridLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizes)

        drawerlayout = findViewById<DrawerLayout>(R.id.drawer_layout3)
        toggle = ActionBarDrawerToggle(this,drawerlayout,R.string.open, R.string.close)
        drawerlayout!!.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var c = findViewById<ImageView>(R.id.c)
        c.setOnClickListener {
            Toast.makeText(this, "Get ready for Quiz", Toast.LENGTH_SHORT).show()
            val myIntent = Intent(this, QuestionsActivity::class.java)
            startActivity(myIntent)
        }

        navView = findViewById<NavigationView>(R.id.navView3)
        navView!!.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.profile_drawer -> {val intent = Intent(this, profilesetup::class.java)
                    startActivity(intent)}
                R.id.quiz_drawer ->{val intent = Intent(this, Quizes::class.java)
                    startActivity(intent)}

                R.id.ask_me_drawer -> {val intent = Intent(this, Askme::class.java)
                    startActivity(intent)}
                R.id.logout_drawer ->{
                    Firebase.auth.signOut()
                    val intent = Intent(this, login::class.java)
                    startActivity(intent)
                }

            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}