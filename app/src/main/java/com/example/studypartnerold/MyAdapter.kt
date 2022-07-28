package com.example.studypartnerold

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
var up :drawer2?=null
class MyAdapter(var taskList:ArrayList<Post>, var listner: OnItemClickListner): RecyclerView.Adapter <MyAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false)

        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        val currentitem = taskList[position]
        holder.t.text = currentitem.title
        holder.d.text = currentitem.desc
        holder.date.text = currentitem.due_date

    }

    override fun getItemCount(): Int {
        return taskList.size
    }




    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView),
    View.OnClickListener{

            var t = itemView.findViewById<TextView>(R.id.title_dis)
            var d = itemView.findViewById<TextView>(R.id.description_dis)
            var date = itemView.findViewById<TextView>(R.id.duedate_dis)

            init{
                itemView.setOnClickListener(this)
            }

            override fun onClick(p0: View?) {
                val position = adapterPosition


                if(position!=RecyclerView.NO_POSITION) {
                    listner.OnItemClick(position)
                }
        }

    }
    interface OnItemClickListner{
        fun OnItemClick(position: Int)
    }

}
