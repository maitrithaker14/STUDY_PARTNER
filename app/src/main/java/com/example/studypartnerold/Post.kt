package com.example.studypartnerold




data class Post(var title: String? = "",
           var desc: String? = "",
           var due_date: String? = "",
           var uid_post: String? = "") {


        fun toMap(): Map<String, Any?> {
            return mapOf(


                "title" to title,
                "desc" to desc,
                "due_date" to due_date,
                "uid" to uid_post
            )
        }

    override fun toString(): String {
        return "$title, +\t$desc, ${due_date}, ${uid_post}"
    }
}