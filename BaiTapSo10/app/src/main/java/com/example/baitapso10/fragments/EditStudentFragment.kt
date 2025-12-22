package com.example.baitapso10.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.baitapso10.R
import com.example.baitapso10.databinding.FragmentAddStudentBinding
import com.example.baitapso10.util.STUDENT_AGE_KEY
import com.example.baitapso10.util.STUDENT_ID_KEY
import com.example.baitapso10.util.STUDENT_MAJOR_KEY
import com.example.baitapso10.util.STUDENT_NAME_KEY
import com.example.baitapso10.viewmodel.StudentViewModel

class EditStudentFragment : Fragment() {

    private var _binding: FragmentAddStudentBinding? = null
    private val binding get() = _binding!! // Dùng lại layout của Add vì giống nhau
    private val viewModel: StudentViewModel by activityViewModels()
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        // Ẩn FAB nếu có trong layout cha
//        view.findViewById<View>(R.id.fabAdd)?.visibility = View.GONE

        viewModel.selectedStudent.observe(viewLifecycleOwner) { student ->
            if (student != null) {
                binding.etName.setText(student.name)
                binding.etAge.setText(student.age.toString())
                binding.etMajor.setText(student.major)
            }
        }

        binding.btnSave.text = "Update" // Đổi text nút

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val ageStr = binding.etAge.text.toString()
            val major = binding.etMajor.text.toString().trim()

            if (name.isNotEmpty() && ageStr.isNotEmpty() && major.isNotEmpty()) {
                val age = try {
                    ageStr.toInt()
                } catch (e: NumberFormatException) {
                    0
                }

                val updatedStudent = viewModel.selectedStudent.value?.copy(
                    name = name,
                    age = age,
                    major = major
                )

                if (updatedStudent != null) {
                    viewModel.updateStudent(updatedStudent)
                    viewModel.clearSelectedStudent()
                    navController.popBackStack() // Quay lại màn hình danh sách
                }
            }
        }

        binding.btnCancel.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}