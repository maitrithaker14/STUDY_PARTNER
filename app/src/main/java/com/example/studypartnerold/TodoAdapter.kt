package com.example.studypartnerold

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.studypartnerold.databinding.ItemNoteBinding
import com.example.studypartnerold.databinding.UpdateTaskBinding
import com.google.firebase.database.FirebaseDatabase


class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private val todolist = arrayListOf<Todo>()

    fun setData(list: MutableList<Todo>) {
        todolist.clear()
        todolist.addAll(list)
        notifyDataSetChanged()
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNoteBinding.bind(itemView)
        fun bind(todo: Todo) {
            with(binding) {
                titleDis.text = todo.title
                descriptionDis.text = todo.desc
                duedateDis.text = todo.due
                itemView.setOnLongClickListener {
                    showDialog(itemView.context, todo)
                    return@setOnLongClickListener true
                }

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, CreateSubTodoActivity::class.java)
                    intent.putExtra(CreateSubTodoActivity.EXTRA_ID, todo.id)
                    intent.putExtra(CreateSubTodoActivity.EXTRA_TITLE, todo.title)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todolist[position])
    }

    override fun getItemCount(): Int = todolist.size

    private fun showDialog(context: Context, todo: Todo) {
        val builder = AlertDialog.Builder(context)
        val binding = UpdateTaskBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        val alert = builder.create()
        binding.titleUp.setText(todo.title)
        binding.descriptionUp.setText(todo.desc)
        binding.duedateUp.setText(todo.due)
        binding.update.setOnClickListener{


            val ref = FirebaseDatabase.getInstance().getReference("TaskNote")

            val title = binding.titleUp.text.toString().trim()
            val desc = binding.descriptionUp.text.toString().trim()
            val date = binding.duedateUp.text.toString().trim()
            val todos = Todo(todo.id, title, desc,date)

            ref.child(todo.id.toString()).setValue(todos)

            Toast.makeText(context, "Data updated", Toast.LENGTH_SHORT).show()
            alert.dismiss()
        }

        binding.delete.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("TaskNote").child(todo.id.toString())
            //val reference =
                //FirebaseDatabase.getInstance().getReference("SubTodo").child(todo.id.toString())

            ref.removeValue()
           // reference.removeValue()

            Toast.makeText(context, "Data deleted", Toast.LENGTH_SHORT).show()
            alert.dismiss()
        }


        alert.show()
    }
}
