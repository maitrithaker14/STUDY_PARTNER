package com.example.studypartnerold

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var myview: View? = null


    fun setSubname(subname: String?) {
        val msubjectname = myview!!.findViewById<TextView>(R.id.title_dis)
        msubjectname.text = subname
    }

    fun setSubdate(subdate: String?) {
        val msubdate = myview!!.findViewById<TextView>(R.id.duedate_dis)
        msubdate.text = subdate
    }

    fun setSubnote(subnote: String?) {
        val msubnote = myview!!.findViewById<TextView>(R.id.description_dis)
        msubnote.text = subnote
    }
    fun click_recycle(): View? {
        return myview
    }


}