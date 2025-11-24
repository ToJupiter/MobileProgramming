package com.example.baitapso7

// StudentAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private var studentList: MutableList<Student>,
    private val onItemClick: (Student, Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewStudentId: TextView = itemView.findViewById(R.id.textViewStudentId)
        val textViewStudentName: TextView = itemView.findViewById(R.id.textViewStudentName)
        val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]
        holder.textViewStudentId.text = student.studentId
        holder.textViewStudentName.text = student.fullName

        // Handle item click for editing
        holder.itemView.setOnClickListener {
            onItemClick(student, position)
        }

        // Handle delete button click
        holder.buttonDelete.setOnClickListener {
            onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    fun updateList(newList: MutableList<Student>) {
        studentList.clear()
        studentList.addAll(newList)
        notifyDataSetChanged()
    }
}