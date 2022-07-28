package com.example.studypartnerold

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class forgotPassword : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var em:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        auth = FirebaseAuth.getInstance()
        em = findViewById(R.id.reset_pass_email)
        val email = em!!.text.toString().trim()
        val btn = findViewById<Button>(R.id.reset_pass_btn)
        btn.setOnClickListener {
            auth.sendPasswordResetEmail(em!!.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email sent.")
                    }
                    else{
                        Log.d(TAG, ""+task.exception)
                    }
                }
        }
    }
}