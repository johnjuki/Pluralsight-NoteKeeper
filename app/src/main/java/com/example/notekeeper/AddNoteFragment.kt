package com.example.notekeeper

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.notekeeper.databinding.FragmentAddNoteBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var noteTitle: EditText
    private lateinit var noteText: EditText
    private lateinit var spinnerCourses: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpNote()

        displayNote()
    }

    private fun setUpNote() {
        val coursesAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            DataManager.courses.values.toList()
        )
        coursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCourses = binding.spinnerCourses
        spinnerCourses.adapter = coursesAdapter

        noteTitle = binding.textNoteTitle
        noteText = binding.textNoteText
    }

    private fun displayNote() {
        val bundle = arguments
        if (bundle == null) {
            Log.e("AddNote null information", "AddNoteFragment did not receive note information")
            return
        }
        val args = AddNoteFragmentArgs.fromBundle(bundle)

        val position = args.noteIndexPosition

        if (position != -1) {
            val note = DataManager.notes[position]

            val coursePosition = DataManager.courses.values.indexOf(note.courseInfo)
            noteTitle.setText(note.title)
            noteText.setText(note.text)
            spinnerCourses.setSelection(coursePosition)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
