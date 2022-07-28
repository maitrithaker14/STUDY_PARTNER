package com.example.studypartnerold


import android.app.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.io.InputStream
import java.util.*

class NewPost : AppCompatActivity() {
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var userId: String
    private val COLLECTION_NAME = "blog"

    private lateinit var progressBar: ProgressBar
    private lateinit var buttonSave: Button
    private lateinit var editTextDescription: EditText

    lateinit var mActivity: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)
        firebaseFirestore = FirebaseFirestore.getInstance()
        userId = FirebaseAuth.getInstance().currentUser?.uid!!

        progressBar = findViewById(R.id.progress_bar_new_post)

        buttonSave = findViewById(R.id.button_post_new_post)
        editTextDescription = findViewById(R.id.edit_text_new_post)


        mActivity = this
        //imageView.setOnClickListener { circleImageViewClicked() }
        buttonSave.setOnClickListener { saveClicked() }

    }

    private fun saveClicked() {
        progressBar.visibility = View.VISIBLE

        val blogDescription = editTextDescription.text.toString().trim()
        if (!isBlogValid(blogDescription)) {
            progressBar.visibility = View.GONE
            return
        }
        val blogData: MutableMap<String, Any> = HashMap()

        blogData["desc"] = blogDescription
        blogData["user_id"] = userId
        blogData["timestamp"] = FieldValue.serverTimestamp()
        firebaseFirestore
            .collection(COLLECTION_NAME)
            .add(blogData)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(mActivity, "Blog uploaded", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(mActivity, "Could not upload blog", Toast.LENGTH_SHORT).show()
                }
                progressBar.visibility = View.GONE
            }

        progressBar.visibility = View.GONE
    }
    private fun isBlogValid(blogDescription: String): Boolean {
        if (blogDescription.isEmpty()) {
            editTextDescription.error = "Cannot be empty"
            return false
        }
        return true
    }

}