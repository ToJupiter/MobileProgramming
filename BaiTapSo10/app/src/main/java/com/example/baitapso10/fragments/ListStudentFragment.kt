package com.example.baitapso10.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baitapso10.R
import com.example.baitapso10.adapters.StudentAdapter
import com.example.baitapso10.databinding.FragmentListStudentBinding
import com.example.baitapso10.viewmodel.StudentViewModel

class ListStudentFragment : Fragment() {

    private var _binding: FragmentListStudentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StudentViewModel by activityViewModels()
    private lateinit var navController: NavController
    private lateinit var adapter: StudentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setupRecyclerView()

        binding.fabAdd.setOnClickListener {
            navController.navigate(R.id.action_listStudentFragment_to_addStudentFragment)
        }

        viewModel.students.observe(viewLifecycleOwner) { list ->
            adapter.updateList(list)
        }
    }

    private fun setupRecyclerView() {
        adapter = StudentAdapter(
            onEditClick = { student ->
                viewModel.selectStudent(student)
                navController.navigate(R.id.action_listStudentFragment_to_editStudentFragment)
            },
            onDeleteClick = { id ->
                viewModel.deleteStudent(id)
            }
        )
        binding.rvStudents.layoutManager = LinearLayoutManager(context)
        binding.rvStudents.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}