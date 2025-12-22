package com.example.baitapso10.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baitapso10.model.Student

class StudentViewModel : ViewModel() {
    private val _students = MutableLiveData<List<Student>>()
    val students : LiveData<List<Student>> = _students

    private val _selectedStudent = MutableLiveData<Student?>()
    val selectedStudent : LiveData<Student?> = _selectedStudent

    init {
        _students.value = mutableListOf<Student>()
    }

    fun addStudent(student: Student) {
        val currentList = _students.value?.toMutableList() ?: mutableListOf()
        val newId = if (currentList.isEmpty()) 1 else (currentList.maxOfOrNull { it.id } ?: 0) + 1
        student.id = newId
        currentList.add(student)
        _students.value = currentList
    }

    fun updateStudent(updatedStudent: Student) {
        val currentList = _students.value?.toMutableList() ?: mutableListOf()
        val index = currentList.indexOfFirst { it.id == updatedStudent.id }
        if (index != -1) {
            currentList[index] = updatedStudent
            _students.value = currentList
        }
    }

    fun deleteStudent(id: Int) {
        val currentList = _students.value?.toMutableList() ?: mutableListOf()
        currentList.removeAll { it.id == id }
        _students.value = currentList
    }

    fun selectStudent(student: Student?) {
        _selectedStudent.value = student
    }

    fun clearSelectedStudent() {
        _selectedStudent.value = null
    }

}