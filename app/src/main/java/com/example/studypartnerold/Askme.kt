package com.example.studypartnerold

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import java.lang.Math.abs

class Askme : AppCompatActivity() {
    lateinit var toggle : ActionBarDrawerToggle
    private var drawerlayout:DrawerLayout?=null
    var navView : NavigationView? =null
    var askme : MenuItem ?= null
    var pro : MenuItem ?= null
    var quiz : MenuItem ?= null
    var log : MenuItem ?=null
    var newpost:ImageView?=null
    private lateinit var blogItems: ArrayList<BlogPostModel>
    private lateinit var blogId: ArrayList<String>
    private lateinit var recyclerView: RecyclerView
    private lateinit var blogAdapter: MyBlogAdapter

    private lateinit var progressBar: ProgressBar

    private lateinit var firebaseFirestore: FirebaseFirestore
    private val COLLECTION_NAME = "blog"
    private var lastVisible: DocumentSnapshot?=null

    //True only for the first time we load the data
    private var isFirestPageFirstLoad: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_askme)
        drawerlayout = findViewById<DrawerLayout>(R.id.drawer_layout2)
        toggle = ActionBarDrawerToggle(this,drawerlayout,R.string.open, R.string.close)
        drawerlayout!!.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setTitle("")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        askme = findViewById(R.id.ask_me_drawer)
        quiz = findViewById(R.id.quiz_drawer)
        pro = findViewById(R.id.profile_drawer)
        log = findViewById(R.id.logout_drawer)

        newpost = findViewById(R.id.newpost)

        navView = findViewById<NavigationView>(R.id.navView2)
        navView!!.setNavigationItemSelectedListener {
            when(it){
                pro -> {val intent = Intent(this, profilesetup::class.java)
                    startActivity(intent)}
                quiz ->{val intent = Intent(this, Quizes::class.java)
                    startActivity(intent)}

                askme -> {val intent = Intent(this, Askme::class.java)
                    startActivity(intent)}
                log ->{
                    Firebase.auth.signOut()
                    val intent = Intent(this, login::class.java)
                    startActivity(intent)
                }

            }
            true
        }

        firebaseFirestore = FirebaseFirestore.getInstance()
            progressBar = findViewById(R.id.progress_bar_home)

            blogItems = ArrayList()
            blogId = ArrayList()
            blogAdapter = MyBlogAdapter()

            recyclerView =findViewById(R.id.recycler_view_home)
            val layoutManager = LinearLayoutManager(this)
            recyclerView!!.setLayoutManager(layoutManager)


            recyclerView.adapter = blogAdapter

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val isReachedBottom: Boolean = !recyclerView.canScrollVertically(1)
                    if (isReachedBottom) {
                        loadMorePost()
                    }

                }
            })


        getData()

        newpost!!.setOnClickListener{
            startActivity(Intent(this,NewPost::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getData() {

        progressBar.visibility = View.VISIBLE

        val firstQuery: Query = firebaseFirestore
            .collection(COLLECTION_NAME)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(6)

        firstQuery
            .addSnapshotListener { value, error ->

                if (isFirestPageFirstLoad) {
//                    lastVisible = value!!.documents[value.size()]
                }

                for (document in value!!.documentChanges) {
                    if (document.type == DocumentChange.Type.ADDED) {

                        val blogPostModel: BlogPostModel =
                            document.document.toObject(BlogPostModel::class.java)
                        val blogDocumentId: String = document.document.id

                        if (isFirestPageFirstLoad) {
                            blogItems.add(blogPostModel)
                            blogId.add(blogDocumentId)
                        } else {
                            blogItems.add(0, blogPostModel)
                            blogId.add(0, blogDocumentId)
                        }

                    }

                    blogAdapter.updateBlogList(blogItems, blogId)
                }

                isFirestPageFirstLoad = false
                progressBar.visibility = View.GONE

            }

        progressBar.visibility = View.GONE

    }
    private fun loadMorePost() {

        val newQuery: Query = firebaseFirestore
            .collection(COLLECTION_NAME)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .startAfter(lastVisible)
            .limit(3)

        newQuery
            .addSnapshotListener { value, error ->

                if (!value!!.isEmpty) {

                  //  lastVisible = value.documents[value.size() - 1]

                    for (document in value.documentChanges) {
                        if (document.type == DocumentChange.Type.ADDED) {
                            val blogPostModel: BlogPostModel =
                                document.document.toObject(BlogPostModel::class.java)
                            val blogDocumentId: String = document.document.id

                            blogItems.add(blogPostModel)
                            blogId.add(blogDocumentId)
                        }

                        blogAdapter.updateBlogList(blogItems, blogId)
                    }

                }
            }

    }

}
