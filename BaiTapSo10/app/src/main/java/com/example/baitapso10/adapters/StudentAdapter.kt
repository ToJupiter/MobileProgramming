package com.example.baitapso10.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baitapso10.databinding.ItemStudentBinding
import com.example.baitapso10.model.Student

class StudentAdapter(
    private val onEditClick: (Student) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var studentList = listOf<Student>()

    fun updateList(list: List<Student>) {
        studentList = list
        notifyDataSetChanged()
    }

    inner class StudentViewHolder(val binding: ItemStudentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(student: Student) {
            binding.apply {
                tvName.text = student.name
                tvAgeMajor.text = "${student.age} - ${student.major}"
                btnEdit.setOnClickListener { onEditClick(student) }
                btnDelete.setOnClickListener { onDeleteClick(student.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun getItemCount(): Int = studentList.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position])
    }
}