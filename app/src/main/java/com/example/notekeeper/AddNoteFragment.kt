package com.example.notekeeper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.notekeeper.databinding.FragmentAddNoteBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Populate the Spinner with courses contained in DataManager
        val coursesAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, DataManager.courses.values.toList())
        coursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCourses.adapter = coursesAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}