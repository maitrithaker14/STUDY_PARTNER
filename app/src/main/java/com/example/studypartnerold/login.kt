package com.example.studypartnerold

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class login : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    private var email_log: EditText? = null
    private var password: EditText? = null
    private var new_user:TextView? = null
    private var log_btn: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email_log = findViewById<EditText>(R.id.login_email)
        password = findViewById<EditText>(R.id.login_pass)
        var forgot_pass = findViewById<TextView>(R.id.forgot_pass)
        log_btn = findViewById(R.id.login_btn)
        new_user = findViewById(R.id.new_user)
        forgot_pass.setOnClickListener {
            startActivity(Intent(this,forgotPassword::class.java))
        }

        new_user!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        })
        auth = Firebase.auth

        auth = FirebaseAuth.getInstance()

        log_btn!!.setOnClickListener(View.OnClickListener {
            val email_l = email_log!!.text.toString().trim()
            val password = password!!.text.toString().trim()

            if (TextUtils.isEmpty(email_l)) {
                Toast.makeText(applicationContext, "Please Entre your email.", Toast.LENGTH_SHORT)
                    .show()

            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Please Enter your Password", Toast.LENGTH_SHORT)
                    .show()

            } else {
                auth.signInWithEmailAndPassword(email_l, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Login Succesfully",
                                Toast.LENGTH_LONG
                            ).show()
                            val user = auth.currentUser
                            updateUI(user)

                            startActivity(Intent(this,drawer2::class.java))


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(applicationContext, "Error occured", Toast.LENGTH_LONG)
                                .show()


                        }

                    }


            }


        })


    }
    private fun updateUI(user: FirebaseUser?) {

    }
}


