package com.example.studypartnerold

import com.google.firebase.Timestamp

data class BlogPostModel(

    var desc: String = "",
    var user_id: String = "",
    var timestamp: Timestamp? = Timestamp(0, 0)
)