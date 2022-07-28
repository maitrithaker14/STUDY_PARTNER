package com.example.studypartnerold

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseApp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class profilesetup : AppCompatActivity() {
    private var donebtn: Button? = null
    lateinit var auth: FirebaseAuth
    lateinit var storage: FirebaseStorage
    private lateinit var databaseReference: DatabaseReference
    private var f: TextInputEditText? = null
    private var l: TextInputEditText? = null
    private var age: TextInputEditText? = null
    private var role: TextInputEditText? = null
    private var course: TextInputEditText? = null
    private var specification: TextInputEditText? = null
    private var pro_img: ImageView? = null
    lateinit var selected_img: Uri


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profilesetup)

        pro_img = findViewById(R.id.pro_img)
        pro_img!!.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.setType("image/*")
            startActivityForResult(intent, 1)
        }


        donebtn = findViewById(R.id.done)
        f = findViewById(R.id.fname)
        l = findViewById(R.id.lname)
        age = findViewById(R.id.age_tv)
        role = findViewById(R.id.role)
        course = findViewById(R.id.course_tv)
        specification = findViewById(R.id.special_tv)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser
        databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://study-planner-4b6f6-default-rtdb.firebaseio.com/Users")
        val id = databaseReference.push().key.toString()
        donebtn!!.setOnClickListener {
            val f = f!!.text.toString()
            val l = l!!.text.toString()
            val age = age!!.text.toString()
            val role = role!!.text.toString()
            val course = course!!.text.toString()
            val specification = specification!!.text.toString()


            val user = User(f, l, age, role, course, specification)
            if (uid != null) {
                databaseReference.child(id).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //uploadprofilepic()
                        Toast.makeText(applicationContext, "Updated profile", Toast.LENGTH_LONG)
                            .show()


                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Failed to update profile",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            selected_img = data.data!!
            pro_img!!.setImageURI(selected_img)
        }
    }


    private fun uploadprofilepic() {

        //var xyz: String? =null
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid)
        storage = FirebaseStorage.getInstance()
        val reference = storage.reference.child("ProfileImages")
        reference.putFile(selected_img).addOnCompleteListener {
            if (it.isSuccessful) {
                reference.downloadUrl.toString()
                // Toast.makeText(applicationContext,xyz,Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(applicationContext, "Upload Image", Toast.LENGTH_LONG).show()
            }
        }


    }
}
