package com.example.studypartnerold

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private var getstarted:Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getstarted = findViewById<Button>(R.id.getstarted)

        //startActivity(Intent(this, drawer::class.java))

        getstarted!!.setOnClickListener(

            View.OnClickListener
            {
                auth = FirebaseAuth.getInstance()
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    startActivity(Intent(this, login::class.java))
                }
                else{
                    startActivity(Intent(this, SignUp::class.java))
                }
            })

    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.


    }
}