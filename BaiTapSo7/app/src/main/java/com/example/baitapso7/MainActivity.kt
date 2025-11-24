package com.example.baitapso7

// MainActivity.kt
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var editTextStudentId: EditText
    private lateinit var editTextFullName: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonUpdate: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private val studentList = mutableListOf<Student>()
    private var currentEditPosition = -1 // To track which item is being edited

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupRecyclerView()
        setupClickListeners()
    }

    private fun initViews() {
        editTextStudentId = findViewById(R.id.editTextStudentId)
        editTextFullName = findViewById(R.id.editTextFullName)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonUpdate = findViewById(R.id.buttonUpdate)
        recyclerView = findViewById(R.id.recyclerViewStudents)
    }

    private fun setupRecyclerView() {
        adapter = StudentAdapter(studentList,
            onItemClick = { student, position ->
                // Populate EditTexts with the clicked item's data
                editTextStudentId.setText(student.studentId)
                editTextFullName.setText(student.fullName)
                currentEditPosition = position

                // Show Update button, hide Add button
                buttonAdd.visibility = View.GONE
                buttonUpdate.visibility = View.VISIBLE
            },
            onDeleteClick = { position ->
                studentList.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupClickListeners() {
        buttonAdd.setOnClickListener {
            addStudent()
        }

        buttonUpdate.setOnClickListener {
            updateStudent()
        }
    }

    private fun addStudent() {
        val id = editTextStudentId.text.toString().trim()
        val name = editTextFullName.text.toString().trim()

        if (id.isNotEmpty() && name.isNotEmpty()) {
            val newStudent = Student(id, name)
            studentList.add(newStudent)
            adapter.notifyItemInserted(studentList.size - 1) // Notify adapter of new item

            // Clear input fields
            editTextStudentId.text.clear()
            editTextFullName.text.clear()
        }
    }

    private fun updateStudent() {
        if (currentEditPosition != -1) {
            val id = editTextStudentId.text.toString().trim()
            val name = editTextFullName.text.toString().trim()

            if (id.isNotEmpty() && name.isNotEmpty()) {
                // Update the student in the list
                studentList[currentEditPosition] = Student(id, name)
                adapter.notifyItemChanged(currentEditPosition) // Notify adapter of the change

                // Reset UI state
                editTextStudentId.text.clear()
                editTextFullName.text.clear()
                buttonAdd.visibility = View.VISIBLE
                buttonUpdate.visibility = View.GONE
                currentEditPosition = -1
            }
        }
    }
}