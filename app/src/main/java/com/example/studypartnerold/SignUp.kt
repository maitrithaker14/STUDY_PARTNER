package com.example.studypartnerold

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

class SignUp : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private var btnSignUp:Button? = null
    private var inputEmail:EditText? = null
    private var inputPassword:EditText?= null
    private var inputname:EditText?=null
    private var con_pass:EditText?=null
    private var log:TextView?=null
    lateinit var fstore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        inputname = findViewById(R.id.reg_name)
        btnSignUp = findViewById(R.id.reg_btn) as Button
        inputEmail = findViewById(R.id.reg_email) as EditText
        inputPassword = findViewById(R.id.reg_pass) as EditText
        con_pass = findViewById(R.id.conf_pass)
        log = findViewById<TextView>(R.id.log_tv)
        log!!.setOnClickListener(View.OnClickListener{
            startActivity(Intent(this,login::class.java))
        })
// Initialize Firebase Auth
        auth = Firebase.auth

        //var progressBar = findViewById(R.id.) as ProgressBar

        auth = FirebaseAuth.getInstance()


        btnSignUp!!.setOnClickListener(View.OnClickListener {

                val email = inputEmail!!.text.toString().trim()
                val password = inputPassword!!.text.toString().trim()
                val name = inputname!!.text.toString()
                val c_pass = con_pass!!.text.toString().trim()
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(applicationContext, "Enter your email Address!!", Toast.LENGTH_LONG)
                        .show()
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(applicationContext, "Enter your Password", Toast.LENGTH_LONG).show()
                }
                if (password.length < 6) {
                    Toast.makeText(
                        applicationContext,
                        "Password too short, enter mimimum 6 charcters",
                        Toast.LENGTH_LONG
                    ).show()
                }
                if(password!=c_pass)
                {
                    Toast.makeText(
                        applicationContext,
                        "Password does not matches with Confirm password",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else
                {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(applicationContext,"Registered Succesfully",Toast.LENGTH_LONG).show()
                                val user = auth.currentUser
                                updateUI(user)
                                startActivity(Intent(this,login::class.java))



                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.exception)
                                Toast.makeText(applicationContext,"Error occured",Toast.LENGTH_LONG).show()
                                Toast.makeText(baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()

                            }
                        }
                }

        })

    }

    private fun updateUI(user: FirebaseUser?) {

    }


}