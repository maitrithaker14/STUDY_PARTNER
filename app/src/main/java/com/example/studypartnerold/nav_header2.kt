package com.example.studypartnerold

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class nav_header2 : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_header2)
        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        val username= user!!.email
        var nav_name = findViewById<TextView>(R.id.name_hed)
        val nav_email = findViewById<TextView>(R.id.email_hed)

        nav_email.text = username.toString()
        //nav_name.setText(user!!.displayName)
    }
}