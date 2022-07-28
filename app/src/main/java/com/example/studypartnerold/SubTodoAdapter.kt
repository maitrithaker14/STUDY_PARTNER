package com.xridwan.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studypartnerold.R
import com.example.studypartnerold.databinding.ItemNoteBinding
import com.xridwan.todolist.model.SubTodo

class SubTodoAdapter : RecyclerView.Adapter<SubTodoAdapter.SubTodoViewHolder>() {
    private val subTodoList = arrayListOf<SubTodo>()

    fun setData(list: MutableList<SubTodo>) {
        subTodoList.clear()
        subTodoList.addAll(list)
        notifyDataSetChanged()
    }

    inner class SubTodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNoteBinding.bind(itemView)
        fun bind(subTodo: SubTodo) {
            with(binding) {
                titleDis.text = subTodo.subTitle
                duedateDis.text = subTodo.da
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubTodoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return SubTodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubTodoViewHolder, position: Int) {
        holder.bind(subTodoList[position])
    }

    override fun getItemCount(): Int = subTodoList.size
}