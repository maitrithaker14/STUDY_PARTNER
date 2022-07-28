package com.example.studypartnerold

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.studypartnerold.databinding.ActivityAddTaskBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase


class addTask : AppCompatActivity() , View.OnClickListener{
    private lateinit var binding: ActivityAddTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cancle.setOnClickListener{
            startActivity(Intent(this,drawer2::class.java))
        }
        binding.save.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val title = binding.title.text.toString().trim()
        val desc = binding.description.text.toString().trim()
        val date = binding.duedate.text.toString().trim()
        var v : View? =null
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(desc)) {

            Snackbar.make(v!!, "Field is empty", Snackbar.LENGTH_SHORT).show()

        } else {
            val ref = FirebaseDatabase.getInstance().getReference("TaskNote")
            val todoId = ref.push().key

            val todos = Todo(todoId, title, desc,date)

            if (todoId != null) {
                ref.child(todoId).setValue(todos).addOnCompleteListener {
                    Toast.makeText(this,"Task Added",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,drawer2::class.java))
                }
            }
        }
    }

}