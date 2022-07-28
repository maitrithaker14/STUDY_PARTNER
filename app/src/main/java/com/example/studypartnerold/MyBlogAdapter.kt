package com.example.studypartnerold

import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore

import java.util.*
import kotlin.collections.ArrayList

class MyBlogAdapter : RecyclerView.Adapter<MyBlogViewHolder>() {

    private val blogItems: ArrayList<BlogPostModel> = ArrayList()
    private val blogId: ArrayList<String> = ArrayList()

    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var currentUserId: String

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBlogViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.my_blog_list_item, parent, false)
        val viewHolder = MyBlogViewHolder(view)

        mContext = parent.context
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        currentUserId = firebaseAuth.currentUser!!.uid

        return viewHolder
    }

    override fun onBindViewHolder(holder: MyBlogViewHolder, position: Int) {
        val description = blogItems.get(position).desc

        val userId = blogItems.get(position).user_id

        try {
            val timestamp: Long = blogItems.get(position).timestamp!!.seconds
            holder.blogTime.text = getDate(timestamp)
        } catch (e: Exception) {
            holder.blogTime.text = ""
        }

        holder.blogDescription.text = description

        holder.showUserName(userId)

        val currentBlogId = blogId.get(position)

        //Get likes count


        //Get like by user

        //Click to like and dislike


        holder
            .imageViewDelete
            .setOnClickListener {
                deleteBlogPost(currentBlogId, position)
            }

    }

    override fun getItemCount(): Int {
        return blogItems.size
    }

    fun updateBlogList(updatedBlogList: ArrayList<BlogPostModel>, updatedId: ArrayList<String>) {
        blogItems.clear()
        blogItems.addAll(updatedBlogList)

        blogId.clear()
        blogId.addAll(updatedId)

        notifyDataSetChanged()
    }

    fun deleteBlogPost(currentBlogId: String, position: Int) {

        firebaseFirestore
            .collection("blog")
            .document(currentBlogId)
            .delete()
            .addOnCompleteListener {

                if (it.isSuccessful) {
                    Toast.makeText(mContext, "Blog deleted", Toast.LENGTH_SHORT).show()

                    blogItems.removeAt(position)
                    blogId.removeAt(position)

                    notifyDataSetChanged()

                } else {
                    Toast.makeText(mContext, "Could not delete blog", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun getDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        return DateFormat.format("dd-MM-yyyy", calendar).toString()
    }
}

class MyBlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val userNameTextView: TextView = itemView.findViewById(R.id.text_View_username_my_blog)
    val blogTime: TextView = itemView.findViewById(R.id.text_View_time_blog_item)
    val blogDescription: TextView = itemView.findViewById(R.id.text_View_description_blog_item)

    val imageViewDelete: ImageView = itemView.findViewById(R.id.image_view_delete_my_blog)
    private lateinit var firebaseAuth: FirebaseAuth

    fun showUserName(userId: String) {
        var username:String?=null
        firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        username= user!!.email
        showData(username.toString())
        }







    private fun showData(userName: String) {

        userNameTextView.text = userName

        val placeolderRequest = RequestOptions()
        placeolderRequest.placeholder(R.drawable.ic_baseline_image_24)

        Glide.with(itemView.context)
            .setDefaultRequestOptions(placeolderRequest)
    }



}
