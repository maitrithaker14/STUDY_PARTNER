package com.example.studypartnerold

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class category : AppCompatActivity() {
    var questionTxt: TextView? = null
    var textView4: TextView? = null
    var b1: Button? = null
    var b2: android.widget.Button? = null
    var b3: android.widget.Button? = null
    var b4: android.widget.Button? = null
    var correct = 0
    var wrong = 0
    var total = 0

    var computerCount = 0

    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

    }

    fun updateQuestion() {
        val db: String
        computerCount++
        if (computerCount > 10) {
            Toast.makeText(applicationContext, "Game Over", Toast.LENGTH_SHORT).show()
            val myIntent = Intent(this@category, Result::class.java)
            myIntent.putExtra("total", total.toString())
            myIntent.putExtra("correct", correct.toString())
            myIntent.putExtra("incorrect", wrong.toString())
            startActivity(myIntent)
        } else {
            val i = intent
            db = i.getStringExtra("db").toString()
            val x = db.toInt()
            when (x) {
                0 -> startActivity(Intent(this, QuestionsActivity::class.java))
                /*1 -> databaseReference =
                    FirebaseDatabase.getInstance().reference.child("Quiz").child("Categories")
                        .child("Cat2").child("C++Questions").child(computerCount.toString())
                2 -> databaseReference =
                    FirebaseDatabase.getInstance().reference.child("Quiz").child("Categories")
                        .child("Cat3").child("JavaQuestions").child(computerCount.toString())
                3 -> databaseReference =
                    FirebaseDatabase.getInstance().reference.child("Quiz").child("Categories")
                        .child("Cat4").child("DbmsQuestions").child(computerCount.toString())
                4 -> databaseReference =
                    FirebaseDatabase.getInstance().reference.child("Quiz").child("Categories")
                        .child("Cat5").child("WebQuestions").child(computerCount.toString())
                5 -> databaseReference =
                    FirebaseDatabase.getInstance().reference.child("Quiz").child("Categories")
                        .child("Cat6").child("ComiQuestions").child(computerCount.toString())*/
            }
            total++


            fun reverseTimer(Seconds: Int, tv: TextView) {
                object : CountDownTimer((Seconds * 1000 + 1000).toLong(), 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        var seconds = (millisUntilFinished / 1000).toInt()
                        val minutes = seconds / 60
                        seconds = seconds % 60
                        tv.text =
                            String.format("%02d", minutes) + ":" + String.format("%02d", seconds)
                    }

                    override fun onFinish() {
                        tv.text = "Completed"
                        val myIntent = Intent(this@category, Result::class.java)
                        myIntent.putExtra("total", total.toString())
                        myIntent.putExtra("correct", correct.toString())
                        myIntent.putExtra("incorrect", wrong.toString())
                        startActivity(myIntent)
                    }
                }.start()
            }
        }
    }
}
